package com.tire.calc.smart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemSearchBinding
import com.tire.calc.smart.models.domain.Manufacturer

class ModelAdapter(private val onItemClickListener: OnClickListener)
    : RecyclerView.Adapter<SearchVH>()
{
    interface OnClickListener {
        fun onItemClick(modelId: Long)
    }

    private var _items = emptyList<Manufacturer>()

    fun setItems(items: List<Manufacturer>) {
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
        holder.txtTitle.text = item.name

        holder.pnlItems.removeAllViews()
        val inflater = LayoutInflater.from(holder.pnlItems.context)
        item.models.forEach { searchModel ->
            val chip = inflater.inflate(
                R.layout.chip_model,
                holder.pnlItems,
                false
            ) as Chip

            chip.text = searchModel.name
            chip.setOnClickListener {
                onItemClickListener.onItemClick(searchModel.id)
            }
            holder.pnlItems.addView(chip)
        }
    }
}