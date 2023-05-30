package com.tire.calc.smart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.ActivitySizeBinding

class SizeActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySizeBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_size)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController

        if (intent.hasExtra(Constants.SIZE_TYPE) && intent.hasExtra(Constants.SIZE_VALUE)) {
            _binding.titleSize = getString(
                when (intent.getStringExtra(Constants.SIZE_TYPE)) {
                    Constants.SIZE_RIM_HEIGHT -> R.string.select_size_rim_diameter
                    Constants.SIZE_RIM_WIDTH -> R.string.select_size_rim_width
                    Constants.SIZE_RIM_ET -> R.string.select_size_rim_et
                    Constants.SIZE_TIRE_WIDTH -> R.string.select_size_tire_width
                    Constants.SIZE_TIRE_HEIGHT -> R.string.select_size_tire_height
                    else -> R.string.app_name
                }
            )
        }
    }
}