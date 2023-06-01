package com.tire.calc.smart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        _binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val REQUEST_BY_CAR_MODEL = 73
    }
}