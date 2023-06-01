package com.tire.calc.smart.ui.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tire.calc.smart.databinding.ListItemSelectorBinding

class SizeVH(binding: ListItemSelectorBinding) : RecyclerView.ViewHolder(binding.root) {
    val txtValue: TextView = binding.txtValue
}