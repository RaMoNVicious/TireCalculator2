package com.tire.calc.smart.ui.size

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.FragmentSizeBinding
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
            btnByCarModel.paintFlags += Paint.UNDERLINE_TEXT_FLAG

            btnClose.setOnClickListener { dismiss() }
        }
    }
}