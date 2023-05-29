package com.tire.calc.smart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySearchBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController

        _binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}