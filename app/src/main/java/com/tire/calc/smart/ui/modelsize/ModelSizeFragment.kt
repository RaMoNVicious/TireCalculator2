package com.tire.calc.smart.ui.modelsize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.databinding.FragmentModelSizeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModelSizeFragment : Fragment() {

    private lateinit var _binding: FragmentModelSizeBinding

    private val viewModel: ModelSizeViewModel by viewModel()

    private lateinit var adapter: ModelSizeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModelSizeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.lifecycleOwner = this

        adapter = ModelSizeAdapter(object : ModelSizeAdapter.OnClickListener {
            override fun onItemClick(sizeId: Long) {
            }
        })
        _binding.lstSearch.adapter = adapter

        viewModel.sizes.observe(viewLifecycleOwner) {
            Log.d("ITEMS", it?.toString() ?: "NULL")
            adapter.setItems(it ?: emptyList())
        }

        arguments
            ?.getLong(ARGUMENT_MODEL_ID)
            ?.let {
                viewModel.getSizes(it)
            }
    }

    companion object {
        const val ARGUMENT_MODEL_ID = "ARGUMENT_MODEL_ID"
    }
}