package com.tire.calc.smart.ui.wheelsize

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.FragmentWheelSizeBinding
import com.tire.calc.smart.models.domain.SelectedWheel
import com.tire.calc.smart.models.domain.Wheel
import com.tire.calc.smart.models.domain.WheelSize
import com.tire.calc.smart.models.domain.SizeType
import com.tire.calc.smart.ui.SearchActivity
import com.tire.calc.smart.ui.SizeSelectionActivity
import com.tire.calc.smart.ui.search.wheels.WheelsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class WheelSizeFragment : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentWheelSizeBinding

    private val viewModel: WheelSizeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetPopupTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWheelSizeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.apply {
            lifecycleOwner = this@WheelSizeFragment
            btnByCarModel.paintFlags += Paint.UNDERLINE_TEXT_FLAG

            (arguments
                ?.getSerializable(SELECTED_WHEEL) as SelectedWheel)
                .let { showWheel(it) }

            btnClose.setOnClickListener { dismiss() }
        }
    }

    private fun showWheel(selectedWheel: SelectedWheel) {
        _binding.apply {
            btnByCarModel.setOnClickListener {
                startActivityForResult(
                    Intent(activity, SearchActivity::class.java),
                    SearchActivity.REQUEST_BY_CAR_MODEL,
                )
            }

            viewModel.wheelSize.observe(viewLifecycleOwner) { wheel ->
                btnRimWidth.text = String.format("%.1f", wheel.rimWidth)
                btnRimDiameter.text = String.format("%.0f", wheel.rimHeight)
                btnRimET.text = String.format("%.0f", wheel.rimEt)

                btnTireWidth.text = String.format("%.0f", wheel.tireWidth)
                btnTireHeight.text = String.format("%.0f", wheel.tireHeight)
                btnTireDiameter.text = String.format("R%.0f", wheel.rimHeight)

                btnRimDiameter.setOnClickListener {
                    getSize(SizeType.RimHeight, wheel)
                }
                btnRimWidth.setOnClickListener {
                    getSize(SizeType.RimWidth, wheel)
                }
                btnRimET.setOnClickListener {
                    getSize(SizeType.RimET, wheel)
                }

                btnTireWidth.setOnClickListener {
                    getSize(SizeType.TireWidth, wheel)
                }
                btnTireHeight.setOnClickListener {
                    getSize(SizeType.TireHeight, wheel)
                }
                btnTireDiameter.setOnClickListener {
                    getSize(SizeType.RimHeight, wheel)
                }

                btnClose.setOnClickListener {
                    viewModel.saveWheel(selectedWheel)
                    _binding.isLoading = true
                }

                btnFavorites.setOnClickListener {
                    viewModel.setFavorite(wheel)
                }

                viewModel.isFavorite(wheel)
            }

            viewModel.isFavorite.observe(viewLifecycleOwner) {
                isFavorite = it ?: false
            }

            viewModel.saved.observe(viewLifecycleOwner) {
                setFragmentResult(SIZE_DIALOG_FOR_RESULT, bundleOf())
                dismiss()
            }

            viewModel.getWheel(selectedWheel)
        }
    }

    private fun getSize(sizeType: SizeType, wheelSize: WheelSize) {
        startActivityForResult(
            Intent(activity, SizeSelectionActivity::class.java)
                .apply {
                    putExtra(SIZE_TYPE, sizeType)
                    putExtra(
                        SIZE_VALUE,
                        when (sizeType) {
                            SizeType.RimWidth -> wheelSize.rimWidth
                            SizeType.RimHeight -> wheelSize.rimHeight
                            SizeType.RimET -> wheelSize.rimEt
                            SizeType.TireWidth -> wheelSize.tireWidth
                            SizeType.TireHeight -> wheelSize.tireHeight
                        }
                    )
                },
            SizeSelectionActivity.REQUEST_SIZE,
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SizeSelectionActivity.REQUEST_SIZE && resultCode == Activity.RESULT_OK) {
            data?.takeIf { it.hasExtra(SIZE_TYPE) && it.hasExtra(SIZE_VALUE) }
                ?.let {
                    viewModel.setSize(
                        it.getSerializableExtra(SIZE_TYPE) as SizeType,
                        it.getDoubleExtra(SIZE_VALUE, 0.0)
                    )
                }

        } else if (requestCode == SearchActivity.REQUEST_BY_CAR_MODEL && resultCode == Activity.RESULT_OK) {
            data?.takeIf { it.hasExtra(WheelsFragment.ARGUMENT_WHEEL_SIZE) }
                ?.let {
                    viewModel.setWheel(
                        it.getSerializableExtra(WheelsFragment.ARGUMENT_WHEEL_SIZE) as Wheel
                    )
                }
        }
    }

    companion object {
        const val SELECTED_WHEEL = "ARGUMENT_SELECTED_WHEEL"
        const val SIZE_VALUE = "ARGUMENT_SIZE_VALUE"
        const val SIZE_TYPE = "ARGUMENT_SIZE_TYPE"

        const val SIZE_DIALOG_FOR_RESULT = "SIZE_DIALOG_FOR_RESULT"
    }
}