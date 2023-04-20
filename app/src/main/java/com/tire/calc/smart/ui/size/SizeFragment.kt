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
import com.tire.calc.smart.databinding.FragmentSizeBinding
import com.tire.calc.smart.ui.SizeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SizeFragment : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentSizeBinding

    private val viewModel: SizeViewModel by viewModel()

    private val sizeForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // TODO:
        }
    }

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

            btnRimDiameter.setOnClickListener {
                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigate(
                        R.id.action_sizeFragment_to_sizeActivity,
                        Bundle().apply {
                            putInt("KEY", 23)
                        }
                    )
                /*sizeForResult.launch(
                    Intent(activity, SizeActivity::class.java)
                )*/
            }

            btnClose.setOnClickListener { dismiss() }
        }
    }
}