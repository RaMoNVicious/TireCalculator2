package com.tire.calc.smart.ui.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.tire.calc.smart.databinding.ListItemSearchBinding

class SearchVH(binding: ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
    val txtTitle: TextView = binding.txtTitle
    val pnlItems: ChipGroup = binding.pnlItems
}