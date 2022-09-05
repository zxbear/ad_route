package com.cn.anim.ui;

import android.os.Bundle;

import com.cn.anim.R;
import com.zz.routeat.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route("R/sss/ss")
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
