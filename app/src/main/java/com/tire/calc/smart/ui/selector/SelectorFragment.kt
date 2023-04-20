package com.tire.calc.smart.ui.selector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.databinding.FragmentSelectorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectorFragment : Fragment() {

    private lateinit var _binding: FragmentSelectorBinding

    private val viewModel: SelectorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectorBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.lifecycleOwner = this


    }
}