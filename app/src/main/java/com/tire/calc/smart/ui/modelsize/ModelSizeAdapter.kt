package com.tire.calc.smart.ui.modelsize

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemBrandModelsBinding
import com.tire.calc.smart.models.domain.ModelTrimSizes

class ModelSizeAdapter(private val onItemClickListener: OnClickListener) : RecyclerView.Adapter<ModelSizeAdapter.SearchModelVH>() {

    interface OnClickListener {
        fun onItemClick(sizeId: Long)
    }

    private var _items = emptyList<ModelTrimSizes>()

    fun setItems(items: List<ModelTrimSizes>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchModelVH {
        return SearchModelVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_brand_models,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: SearchModelVH, position: Int) {
        val item = _items[position]
        holder.brandName.text = item.trimName

        holder.pnlModels.removeAllViews()
        item.tireSize.forEach { tireSize ->
            val chip = Chip(holder.pnlModels.context)
            chip.text = tireSize.sizeName
            chip.setOnClickListener {
                Log.d("SEARCH ITEM", "$tireSize clicked!")
                onItemClickListener.onItemClick(tireSize.sizeId)
            }
            holder.pnlModels.addView(chip)
        }
    }

    inner class SearchModelVH(binding: ListItemBrandModelsBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        val brandName: Chip = binding.brandName;
        val pnlModels: ChipGroup = binding.pnlModels;
    }
}