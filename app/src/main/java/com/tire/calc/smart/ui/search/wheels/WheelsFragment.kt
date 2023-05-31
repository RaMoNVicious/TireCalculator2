package com.tire.calc.smart.ui.search.wheels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.databinding.FragmentModelSizeBinding
import com.tire.calc.smart.models.domain.Wheel
import com.tire.calc.smart.ui.adapters.ModelSizeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WheelsFragment : Fragment() {

    private lateinit var _binding: FragmentModelSizeBinding

    private val viewModel: WheelsViewModel by viewModel()

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

        adapter = ModelSizeAdapter()
        _binding.lstSearch.adapter = adapter

        viewModel.sizes.observe(viewLifecycleOwner) { items ->
            adapter.setItems(
                items,
                object : ModelSizeAdapter.OnClickListener {
                    override fun onItemClick(wheel: Wheel) {
                        activity?.let{
                            it.setResult(
                                Activity.RESULT_OK,
                                Intent().putExtra(ARGUMENT_WHEEL_SIZE, wheel)
                            )
                            it.finish()
                        }
                    }
                }
            )
        }

        arguments
            ?.getLong(ARGUMENT_MODEL_ID)
            ?.let {
                viewModel.getSizes(it)
            }
    }

    companion object {
        const val ARGUMENT_MODEL_ID = "ARGUMENT_MODEL_ID"
        const val ARGUMENT_WHEEL_SIZE = "ARGUMENT_WHEEL_SIZE"
    }
}