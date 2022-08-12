```
A framework for assisting in the renovation of Android app componentization
```

**I. Configuration**
1. Adding dependencies and configurations
```
android {
   defaultConfig {
       ... ...
       javaCompileOptions{
           annotationProcessorOptions{
               arguments = [ moduleName : project.getName() ]
           }
       }
   }     
}

dependencies {
    implementation 'com.github.zxbear.ad_route:route_api:v.1.0.2'
    annotationProcessor ''com.github.zxbear.ad_route:route_compiler:v.1.0.2'
}
```
2. Add annotations
```
@Route("com/cn/test")
public class test1Activity extends Activity{...}
```
3. Initialize the SDK
```
// 1. Simple jump within application
Router.init(this);

// 2. Jump with your need skip code
Router.getInstance().starActivity(this,"com/cn/test");
```

**I. Notes**
[笔记链接](http://note.youdao.com/noteshare?id=97991a678d2862557ef388622a8e52e5&sub=1F6043489AC14F13A82967CB88935E77)

