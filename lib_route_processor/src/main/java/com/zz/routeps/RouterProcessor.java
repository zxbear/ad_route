package com.zz.routeps;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.zz.routeat.Route;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 生成注册文件-手动生成如下：
 * mian -> resources -> META-INF -> services -> javax.annotation.processing.Processor 内容为 RouterProcessor具体路径
 * com.zz.routeps.RouterProcessor
 */
@AutoService(Processor.class)
// 支持的 Java 版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RouterProcessor extends AbstractProcessor {
    private Filer filter;
    private String moduleName;
    private Elements elementUtils;
    //javapoet所需参数
    private final static String pack_name = "com.zxbear.routers";
    private final static String iroute_path = "com.zz.route.IRoteLoad";
    private final static String activity_path = "android.app.Activity";
    private final static String iroute_override = "loadInto";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filter = processingEnv.getFiler();
        Map<String, String> options = processingEnv.getOptions();
        moduleName = options.get("moduleName");
        elementUtils = processingEnv.getElementUtils();
        System.out.println("\nstart -------------------------------->\n");
        System.out.println("moduleName = " + moduleName);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 返回所有需要监听处理的注解集合
        Set<String> strings = new HashSet<>();
        strings.add(Route.class.getCanonicalName());
        return strings;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!set.isEmpty()) {
            Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(Route.class);
            generatedClass(routeElements);
            System.out.println("\nend -------------------------------->\n");
            return true;
        }
        return false;
    }

    /**
     * javaPoet
     *
     * @param routeElements
     */
    private void generatedClass(Set<? extends Element> routeElements) {
        String logs = "[";//日志打印
        //构建方法体入参
        ParameterizedTypeName inputMapTypeOfRoot = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(
                        ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(elementUtils.getTypeElement(activity_path))))
        );
        ParameterSpec rootParamSpec = ParameterSpec.builder(inputMapTypeOfRoot, "routes").build();
        //构建方法
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(iroute_override)
                .addAnnotation(Override.class)//方法体注解，即实现注解
                .addModifiers(Modifier.PUBLIC)//类修饰
                .addParameter(rootParamSpec);//方法体入参
        //构建方法体
        for (Element item : routeElements) {
            //获取注解
            Route router = item.getAnnotation(Route.class);
            //获取对应activity包管理类
            PackageElement packageElement = elementUtils.getPackageOf(item);
            methodSpec.addStatement("routes.put($S, $T.class)",
                    router.value(),
                    ClassName.get(
                            packageElement.getQualifiedName().toString(),
                            item.getSimpleName().toString()));
            logs += "{\"" + router.value() + "\":\"" + item.getSimpleName() + "\"}";
        }
        //构建类
        TypeSpec classSpec = TypeSpec.classBuilder(moduleName + "Router")//类名名
                .addModifiers(Modifier.PUBLIC)//类修饰
                .addSuperinterface(ClassName.get(elementUtils.getTypeElement(iroute_path)))//类实现接口
                .addMethod(methodSpec.build())
                .build();
        //写入文件
        try {
           JavaFile.builder(pack_name, classSpec).build().writeTo(filter);
            System.out.println("routers = " + logs + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传统字符串拼接
     *
     * @param routeElements
     */
    private void generatedClass1(Set<? extends Element> routeElements) {
        StringBuilder sb = new StringBuilder();
        String atPacks = "";//存放当前activity的引入路径
        String routPuts = "";//存放put
        String logs = "[";//日志打印
        for (Element item : routeElements) {
            //获取注解
            Route router = item.getAnnotation(Route.class);
            //获取对应activity包管理类
            PackageElement packageElement = elementUtils.getPackageOf(item);
            //拼接引入包
            atPacks += "import " + packageElement.getQualifiedName() + "." + item.getSimpleName() + ";\n";
            //拼接put资源
            routPuts += "routers.put(\"" + router.value() + "\"," + item.getSimpleName() + ".class);\n";

            logs += "{\"" + router.value() + "\":\"" + item.getSimpleName() + "\"}";
        }
        System.out.println("routers = " + logs + "]");
        sb.append("package " + pack_name + ";\n");
        sb.append("import android.app.Activity;\n");
        sb.append("import com.zz.route.IRoteLoad;\n");
        sb.append("import java.util.Map;\n\n");
        sb.append(atPacks);
        sb.append("public class ");
        sb.append(moduleName + "Router implements IRoteLoad {\n");
        sb.append("@Override\n");
        sb.append("public void loadInto(Map<String, Class<? extends Activity>> routers) {\n");

        sb.append(routPuts);
        sb.append("}\n" + "}");

        try {
            JavaFileObject sourceFile = filter.createSourceFile("com.zz.routers." + moduleName + "Router");
            OutputStream outputStream = sourceFile.openOutputStream();
            outputStream.write(sb.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
