package com.project.bitereg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.bitereg.R
import com.project.bitereg.databinding.SelectionItemBinding

class SelectionListAdapter(
    private val selectionItemList: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<SelectionListAdapter.SelectionItemViewHolder>() {

    class SelectionItemViewHolder(val binding: SelectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectionItemViewHolder(
        SelectionItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.selection_item, parent, false)
        )
    )

    override fun onBindViewHolder(holder: SelectionItemViewHolder, position: Int) {
        holder.binding.selectionItemText.apply {
            text = selectionItemList[position]
            setOnClickListener { onClick(selectionItemList[position]) }
        }
    }

    override fun getItemCount() = selectionItemList.size
}