package com.tire.calc.smart.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemBrandModelsBinding
import com.tire.calc.smart.models.domain.Manufacturer

class ModelAdapter(private val onItemClickListener: OnClickListener)
    : RecyclerView.Adapter<ModelAdapter.SearchModelVH>()
{
    interface OnClickListener {
        fun onItemClick(modelId: Long)
    }

    private var _items = emptyList<Manufacturer>()

    fun setItems(items: List<Manufacturer>) {
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
        holder.brandName.text = item.manufacturerName

        holder.pnlModels.removeAllViews()
        item.models.forEach { searchModel ->
            val chip = Chip(holder.pnlModels.context)
            chip.text = searchModel.modelName
            chip.setOnClickListener {
                onItemClickListener.onItemClick(searchModel.modelId)
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