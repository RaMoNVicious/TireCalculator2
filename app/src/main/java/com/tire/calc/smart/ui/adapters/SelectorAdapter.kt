package com.tire.calc.smart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tire.calc.smart.databinding.ListItemSelectorBinding

class SelectorAdapter : RecyclerView.Adapter<SelectorVH>() {

    interface OnClickListener {
        fun onClick(index: Int)
    }

    private var _items = emptyList<String>()

    private var _onClickListener: OnClickListener? = null

    fun setItems(items: List<String>, onClickListener: OnClickListener) {
        _items = items
        _onClickListener = onClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorVH {
        return SelectorVH(
            ListItemSelectorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: SelectorVH, position: Int) {
        holder.txtValue.text = _items[position]
        holder.txtValue.setOnClickListener {
            _onClickListener?.onClick(position)
        }
    }
}