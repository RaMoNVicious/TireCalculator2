package com.tire.calc.smart.ui.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tire.calc.smart.databinding.ListItemCompareBinding

class CompareVH(binding: ListItemCompareBinding) : RecyclerView.ViewHolder(binding.root) {
    val txtValueReference: TextView = binding.txtValueReference
    val txtValueCandidate: TextView = binding.txtValueCandidate
    val txtDifference: TextView = binding.txtDifference
    val txtTitle: TextView = binding.txtTitle
}