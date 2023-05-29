package com.tire.calc.smart.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemBrandModelsBinding
import com.tire.calc.smart.databinding.ListItemSearchBinding
import com.tire.calc.smart.models.domain.ModelTrimSizes

class ModelSizeAdapter(private val onItemClickListener: OnClickListener) :
    RecyclerView.Adapter<SearchVH>() {

    interface OnClickListener {
        fun onItemClick(sizeId: Long)
    }

    private var _items = emptyList<ModelTrimSizes>()

    fun setItems(items: List<ModelTrimSizes>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(
            ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val item = _items[position]
        holder.txtTitle.text = item.trimName

        holder.pnlItems.removeAllViews()
        val inflater = LayoutInflater.from(holder.pnlItems.context)
        item.tireSize.forEach { tireSize ->
            val chip = inflater.inflate(
                R.layout.chip_wheel,
                holder.pnlItems,
                false
            ) as Chip

            chip.text = tireSize.sizeName
            chip.setOnClickListener {
                Log.d("SEARCH ITEM", "$tireSize clicked!")
                onItemClickListener.onItemClick(tireSize.sizeId)
            }

            holder.pnlItems.addView(chip)
        }
    }
}