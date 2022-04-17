package com.tire.calc.smart.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.databinding.FragmentMainBinding
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

        _binding.lifecycleOwner = this

        viewModel.wheelInfo.observe(viewLifecycleOwner) { wheelInfo ->
            Log.d(
                "WHEEL",
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

        viewModel.updateCarInfo()
    }
}