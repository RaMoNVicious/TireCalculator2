package com.tire.calc.smart.ui.size

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
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
                    getSize(Constants.SIZE_RIM_HEIGHT, wheelSize)
                }
                btnRimWidth.setOnClickListener {
                    getSize(Constants.SIZE_RIM_WIDTH, wheelSize)
                }
                btnRimET.setOnClickListener {
                    getSize(Constants.SIZE_RIM_ET, wheelSize)
                }

                btnTireWidth.setOnClickListener {
                    getSize(Constants.SIZE_TIRE_WIDTH, wheelSize)
                }
                btnTireHeight.setOnClickListener {
                    getSize(Constants.SIZE_TIRE_HEIGHT, wheelSize)
                }
                btnTireDiameter.setOnClickListener {
                    getSize(Constants.SIZE_RIM_HEIGHT, wheelSize)
                }

                btnClose.setOnClickListener {
                    viewModel.saveWheel(selectedWheel)
                    _binding.isLoading = true
                }

                viewModel.checkIsFavorite(wheelSize)
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

    private fun getSize(sizeType: String, wheelSize: WheelSize) {
        startActivityForResult(
            Intent(activity, SizeActivity::class.java)
                .apply {
                    putExtra(Constants.SIZE_TYPE, sizeType)
                    putExtra(
                        Constants.SIZE_VALUE,
                        when (sizeType) {
                            Constants.SIZE_RIM_WIDTH -> wheelSize.rimWidth
                            Constants.SIZE_RIM_HEIGHT -> wheelSize.rimHeight
                            Constants.SIZE_RIM_ET -> wheelSize.rimEt
                            Constants.SIZE_TIRE_WIDTH -> wheelSize.tireWidth
                            Constants.SIZE_TIRE_HEIGHT -> wheelSize.tireHeight
                            else -> null
                        }
                    )
                },
            Constants.REQUEST_SIZE,
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_SIZE && resultCode == Activity.RESULT_OK) {
            data?.takeIf { it.hasExtra(Constants.SIZE_TYPE) && it.hasExtra(Constants.SIZE_VALUE) }
                ?.let {
                    viewModel.setSize(
                        it.getStringExtra(Constants.SIZE_TYPE) ?: "",
                        it.getDoubleExtra(Constants.SIZE_VALUE, 0.0)
                    )
                }

        }
    }

    companion object {
        const val SIZE_DIALOG_FOR_RESULT = "SIZE_DIALOG_FOR_RESULT"
    }
}