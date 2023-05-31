package com.tire.calc.smart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ActivitySizeBinding
import com.tire.calc.smart.models.domain.SizeType
import com.tire.calc.smart.ui.wheelsize.WheelSizeFragment

class SizeActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_size)

        if (intent.hasExtra(WheelSizeFragment.SIZE_TYPE)) {
            _binding.titleSize = getString(
                when (intent.getSerializableExtra(WheelSizeFragment.SIZE_TYPE) as SizeType) {
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