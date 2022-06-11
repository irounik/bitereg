package com.project.bitereg.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.bitereg.R
import com.project.bitereg.databinding.IssueItemLayoutBinding
import com.project.bitereg.models.Issue

class IssuesAdapter(
    private val issues: MutableList<Issue>,
    private val onIssueClicked: (Issue) -> Unit
) : RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>() {

    inner class IssuesViewHolder(val binding: IssueItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssuesViewHolder(
        IssueItemLayoutBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.issue_item_layout, parent, false)
        )
    )

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val item = issues[position]
        with(holder.binding) {
            issueTitle.text = item.title
            issueDescriptionTv.text = item.description
            root.setOnClickListener { onIssueClicked(item) }
        }
    }

    override fun getItemCount(): Int = issues.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Issue>) {
        issues.clear()
        issues.addAll(newList)
        notifyDataSetChanged()
    }

}