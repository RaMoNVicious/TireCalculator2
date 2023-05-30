package com.tire.calc.smart.ui.selector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.app.Constants
import com.tire.calc.smart.databinding.FragmentSelectorBinding
import com.tire.calc.smart.ui.adapters.SizeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectorFragment : Fragment() {

    private lateinit var _binding: FragmentSelectorBinding

    private val viewModel: SelectorViewModel by viewModel()

    private lateinit var adapter: SizeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectorBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.lifecycleOwner = this

        adapter = SizeAdapter()
        _binding.listSize.adapter = adapter

        activity?.intent
            ?.takeIf { it.hasExtra(Constants.SIZE_TYPE) && it.hasExtra(Constants.SIZE_VALUE) }
            ?.getStringExtra(Constants.SIZE_TYPE)
            ?.let { sizeType ->
                viewModel.sizes.observe(viewLifecycleOwner) { items ->
                    adapter.setItems(
                        items = items.map {
                            when (sizeType) {
                                Constants.SIZE_RIM_WIDTH -> "%.1f".format(it)
                                else -> "%.0f".format(it)
                            }
                        },
                        onClickListener = object : SizeAdapter.OnClickListener {
                            override fun onClick(index: Int) {
                                activity?.let{
                                    it.setResult(
                                        Activity.RESULT_OK,
                                        Intent()
                                            .putExtra(Constants.SIZE_TYPE, sizeType)
                                            .putExtra(Constants.SIZE_VALUE, items[index])
                                    )
                                    it.finish()
                                }
                            }
                        }
                    )
                }

                viewModel.getSizes(sizeType)
            }
    }

}