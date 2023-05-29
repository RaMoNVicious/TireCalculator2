package com.tire.calc.smart.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.ActivitySizeBinding
import com.tire.calc.smart.ui.adapters.SelectorAdapter

class SizeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_size)

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

            /*val items = (45..130 step 5).toList().map { it.toFloat() / 10F }
            _binding.listSizes.adapter = SelectorAdapter(
                items = items.map { "%.1f".format(it) },
                onClickListener = object : SelectorAdapter.OnClickListener {
                    override fun onClick(index: Int) {
                        Log.d("Ruler", "Selected value = ${items[index]}")
                    }
                }
            )*/
            // TODO:
        }
    }
}