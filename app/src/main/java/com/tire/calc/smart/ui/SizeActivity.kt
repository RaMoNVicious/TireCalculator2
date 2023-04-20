package com.tire.calc.smart.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tire.calc.smart.R

class SizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size)

        if (Intent().hasExtra("KEY")) {
            Log.d("SIZE", "${Intent().getIntExtra("KEY", 0)}")
        }
    }
}