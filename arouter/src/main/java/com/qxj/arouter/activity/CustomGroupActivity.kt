package com.qxj.arouter.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.qxj.arouter.R

@Route(path = "/com/CustomGroupActivity", group = "customGroup")
class CustomGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_group)
    }
}
