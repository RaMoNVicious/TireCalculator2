package com.tire.calc.smart.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.FragmentMainBinding
import com.tire.calc.smart.models.domain.SelectedWheel
import com.tire.calc.smart.ui.wheelsize.WheelSizeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.apply {
            lifecycleOwner = this@MainFragment

            sizeReference.setOnClickListener {
                editSize(SelectedWheel.Reference)
            }

            sizeCandidate.setOnClickListener {
                editSize(SelectedWheel.Candidate)
            }
        }

        viewModel.wheelReference.observe(viewLifecycleOwner) { wheelInfo ->
            Log.d(
                "WHEEL REFERENCE",
                "on WheelInfo observe\n" +
                        "Caption: ${wheelInfo.getTireLabel()}, ${wheelInfo.getRimLabel()}\n" +
                        "Wheel Height: ${wheelInfo.getWheelHeight()}\n" +
                        "Tire Width: ${wheelInfo.getTireWidth()}\n" +
                        "Tire Side Height: ${wheelInfo.getTireSideHeight()}\n" +
                        "Rim Width: ${wheelInfo.getRimWidth()}\n" +
                        "Rim Height: ${wheelInfo.getRimDiameter()}\n" +
                        "Revs per km: ${wheelInfo.getRevsPer()}\n" // TODO: add results values
            )

            _binding.apply {
                lblTireReference.text = wheelInfo.getTireLabel()
                lblRimReference.text = wheelInfo.getRimLabel()
            }
        }

        viewModel.wheelCandidate.observe(viewLifecycleOwner) { wheelInfo ->
            _binding.apply {
                lblTireCandidate.text = wheelInfo.getTireLabel()
                lblRimCandidate.text = wheelInfo.getRimLabel()
            }
        }
    }

    private fun editSize(selectedWheel: SelectedWheel) {
        setFragmentResultListener(WheelSizeFragment.SIZE_DIALOG_FOR_RESULT) { key, bundle ->
            viewModel.getWheels()
        }

        activity
            ?.findNavController(R.id.nav_host_fragment_container)
            ?.navigate(
                R.id.action_mainFragment_to_sizeFragment,
                Bundle().apply {
                    putSerializable(
                        Constants.SELECTED_WHEEL,
                        selectedWheel
                    )
                }
            )
    }
}