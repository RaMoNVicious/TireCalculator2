package com.tire.calc.smart.ui.search.models

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.FragmentSearchModelBinding
import com.tire.calc.smart.ui.adapters.ModelAdapter
import com.tire.calc.smart.ui.search.wheels.WheelsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModelsFragment : Fragment() {

    private lateinit var _binding: FragmentSearchModelBinding

    private val viewModel: ModelsViewModel by viewModel()

    private lateinit var adapter: ModelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchModelBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.lifecycleOwner = this

        adapter = ModelAdapter(object : ModelAdapter.OnClickListener {
            override fun onItemClick(modelId: Long) {
                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigate(
                        R.id.action_searchFragment_to_modelSizeFragment,
                        Bundle().apply {
                            putLong(
                                WheelsFragment.ARGUMENT_MODEL_ID,
                                modelId
                            )
                        }
                    )
            }
        })
        _binding.lstSearch.adapter = adapter

        _binding.txtSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.search(text.toString())
        }

        viewModel.manufacturers.observe(viewLifecycleOwner) {
            Log.d("ITEMS", it?.toString() ?: "NULL")
            adapter.setItems(it ?: emptyList())
        }
    }
}