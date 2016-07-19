package com.gm.jianmerchant.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initListener();
        initData();
    }

    protected abstract void setContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

}
