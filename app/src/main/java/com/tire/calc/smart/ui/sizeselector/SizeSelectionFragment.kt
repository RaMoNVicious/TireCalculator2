package com.tire.calc.smart.ui.sizeselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tire.calc.smart.databinding.FragmentSelectorBinding
import com.tire.calc.smart.models.domain.SizeType
import com.tire.calc.smart.ui.adapters.SizeAdapter
import com.tire.calc.smart.ui.wheelsize.WheelSizeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SizeSelectionFragment : Fragment() {

    private lateinit var _binding: FragmentSelectorBinding

    private val viewModel: SizeSelectionViewModel by viewModel()

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
            ?.takeIf {
                it.hasExtra(WheelSizeFragment.SIZE_TYPE)
                        && it.hasExtra(WheelSizeFragment.SIZE_VALUE)
            }
            ?.let { intent ->
                val sizeType = intent.getSerializableExtra(WheelSizeFragment.SIZE_TYPE) as SizeType
                val sizeValue = intent.getDoubleExtra(WheelSizeFragment.SIZE_VALUE, 0.0)

                viewModel.sizes.observe(viewLifecycleOwner) { items ->
                    adapter.setItems(
                        items = items.map {
                            when (sizeType) {
                                SizeType.RimWidth -> "%.1f".format(it)
                                else -> "%.0f".format(it)
                            }
                        },
                        onClickListener = object : SizeAdapter.OnClickListener {
                            override fun onClick(index: Int) {
                                activity?.let{
                                    it.setResult(
                                        Activity.RESULT_OK,
                                        Intent()
                                            .putExtra(WheelSizeFragment.SIZE_TYPE, sizeType)
                                            .putExtra(WheelSizeFragment.SIZE_VALUE, items[index])
                                    )
                                    it.finish()
                                }
                            }
                        }
                    )

                    items.indexOf(sizeValue)
                        .takeIf { it != -1 }
                        ?.let { index ->
                            _binding.listSize.scrollToPosition(index)
                        }

                }
                viewModel.getSizes(sizeType)
            }
    }

}