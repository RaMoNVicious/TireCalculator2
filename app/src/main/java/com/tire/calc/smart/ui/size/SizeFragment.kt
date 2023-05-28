package com.tire.calc.smart.ui.size

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.FragmentSizeBinding
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.ui.SizeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SizeFragment : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentSizeBinding

    private val viewModel: SizeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetPopupTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSizeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.apply {
            lifecycleOwner = this@SizeFragment
            btnByCarModel.paintFlags += Paint.UNDERLINE_TEXT_FLAG

            arguments
                ?.getString(Constants.SELECTED_WHEEL)
                ?.let { showWheel(it) }

            btnClose.setOnClickListener { dismiss() }
        }
    }

    private fun showWheel(selectedWheel: String) {
        _binding.apply {
            btnByCarModel.setOnClickListener {
                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigate(
                        R.id.action_sizeFragment_to_searchActivity,
                        Bundle().apply {
                            putString(
                                Constants.SELECTED_WHEEL,
                                selectedWheel
                            )
                        }
                    )
            }

            viewModel.wheel.observe(viewLifecycleOwner) { wheelSize ->
                btnRimDiameter.text = String.format("%.0f", wheelSize.rimHeight)
                btnRimWidth.text = String.format("%.1f", wheelSize.rimWidth)
                btnRimET.text = String.format("%.0f", wheelSize.rimEt)

                btnTireWidth.text = String.format("%.0f", wheelSize.tireWidth)
                btnTireHeight.text = String.format("%.0f", wheelSize.tireHeight)
                btnTireDiameter.text = String.format("R%.0f", wheelSize.rimHeight)

                btnRimDiameter.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_RIM_HEIGHT, wheelSize)
                        )
                }
                btnRimWidth.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_RIM_WIDTH, wheelSize)
                        )
                }
                btnRimET.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_RIM_ET, wheelSize)
                        )
                }

                btnTireWidth.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_TIRE_WIDTH, wheelSize)
                        )
                }
                btnTireHeight.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_TIRE_HEIGHT, wheelSize)
                        )
                }
                btnTireDiameter.setOnClickListener {
                    activity
                        ?.findNavController(R.id.nav_host_fragment_container)
                        ?.navigate(
                            R.id.action_sizeFragment_to_sizeActivity,
                            getSizeBundle(Constants.SIZE_RIM_HEIGHT, wheelSize)
                        )
                }
            }

            viewModel.getWheel(selectedWheel)
        }
    }

    private fun getSizeBundle(sizeType: String, wheelSize: WheelSize): Bundle {
        return Bundle().apply {
            putString(Constants.SIZE_TYPE, sizeType)
            putSerializable(Constants.SIZE_VALUE, wheelSize)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        
    }
}