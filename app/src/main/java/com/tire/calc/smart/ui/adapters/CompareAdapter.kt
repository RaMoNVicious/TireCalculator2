package com.tire.calc.smart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tire.calc.smart.R
import com.tire.calc.smart.databinding.ListItemCompareBinding
import com.tire.calc.smart.models.domain.WheelCompare

class CompareAdapter : RecyclerView.Adapter<CompareVH>() {

    private var _items: List<WheelCompare> = emptyList()

    fun setItems(items: List<WheelCompare>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompareVH {
        return CompareVH(
            ListItemCompareBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: CompareVH, position: Int) {
        val item = _items[position]
        val resources = holder.itemView.context.resources

        holder.txtTitle.text = resources.getText(item.nameResId)
        holder.txtValueReference.text = resources.getString(
            R.string.compare_format,
            item.valueReference
        )
        holder.txtValueCandidate.text = resources.getString(
            R.string.compare_format,
            item.valueCandidate
        )
        holder.txtDifference.text = resources.getString(
            if (item.getDifference() < 0)
                R.string.compare_format else R.string.compare_format_plus,
            item.getDifference()
        )
    }
}