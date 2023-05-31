package com.tire.calc.smart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.ActivitySizeBinding
import com.tire.calc.smart.models.domain.SizeType

class SizeActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySizeBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_size)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController

        if (intent.hasExtra(Constants.SIZE_TYPE) && intent.hasExtra(Constants.SIZE_VALUE)) {
            _binding.titleSize = getString(
                when (intent.getSerializableExtra(Constants.SIZE_TYPE) as SizeType) {
                    SizeType.RimHeight -> R.string.select_size_rim_diameter
                    SizeType.RimWidth -> R.string.select_size_rim_width
                    SizeType.RimET -> R.string.select_size_rim_et
                    SizeType.TireWidth -> R.string.select_size_tire_width
                    SizeType.TireHeight -> R.string.select_size_tire_height
                }
            )
        }
    }

    companion object {
        const val REQUEST_SIZE = 42
    }
}