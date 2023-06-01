package com.tire.calc.smart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemSearchBinding
import com.tire.calc.smart.models.domain.TrimWheels
import com.tire.calc.smart.models.domain.Wheel

class ModelSizeAdapter : RecyclerView.Adapter<SearchVH>() {

    interface OnClickListener {
        fun onItemClick(wheel: Wheel)
    }

    private var _items = emptyList<TrimWheels>()

    private var _onClickListener: OnClickListener? = null

    fun setItems(items: List<TrimWheels>, onItemClickListener: OnClickListener) {
        _items = items
        _onClickListener = onItemClickListener
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
        item.wheels.forEach { wheel ->
            val chip = inflater.inflate(
                R.layout.chip_wheel,
                holder.pnlItems,
                false
            ) as Chip

            chip.text = wheel.size
            chip.setOnClickListener {
                _onClickListener?.onItemClick(wheel)
            }

            holder.pnlItems.addView(chip)
        }
    }
}