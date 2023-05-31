package com.tire.calc.smart.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.FragmentMainBinding
import com.tire.calc.smart.models.domain.SelectedWheel
import com.tire.calc.smart.ui.adapters.CompareAdapter
import com.tire.calc.smart.ui.wheelsize.WheelSizeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: CompareAdapter

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

        adapter = CompareAdapter()

        _binding.apply {
            lifecycleOwner = this@MainFragment

            lstCompare.adapter = adapter

            sizeReference.setOnClickListener {
                editSize(SelectedWheel.Reference)
            }

            sizeCandidate.setOnClickListener {
                editSize(SelectedWheel.Candidate)
            }
        }

        viewModel.wheelReference.observe(viewLifecycleOwner) { wheelInfo ->
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

        viewModel.wheelCompare.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }
    }

    private fun editSize(selectedWheel: SelectedWheel) {
        setFragmentResultListener(WheelSizeFragment.SIZE_DIALOG_FOR_RESULT) { _, _ ->
            viewModel.getWheels()
        }

        activity
            ?.findNavController(R.id.nav_host_fragment_container)
            ?.navigate(
                R.id.action_mainFragment_to_sizeFragment,
                Bundle().apply {
                    putSerializable(
                        WheelSizeFragment.SELECTED_WHEEL,
                        selectedWheel
                    )
                }
            )
    }
}