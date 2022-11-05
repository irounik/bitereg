package com.project.bitereg.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.bitereg.R
import com.project.bitereg.databinding.PostItemLayoutBinding
import com.project.bitereg.models.Post

class FeedAdapter(
    private val posts: MutableList<Post>,
    private val onPostClicked: (Post) -> Unit
) :
    RecyclerView.Adapter<FeedAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        PostItemLayoutBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout, parent, false)
        )
    )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = posts[position]
        with(holder.binding) {
            postTitle.text = item.title
            postDescriptionTv.text = item.description
            Glide.with(root).load(item.imageUrl)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(postImage)
            Glide.with(root).load(item.userProfileImgUrl)
                .placeholder(R.drawable.ic_user)
                .into(userPic)
            root.setOnClickListener { onPostClicked(item) }
        }
    }

    override fun getItemCount(): Int = posts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Post>) {
        posts.clear()
        posts.addAll(newList)
        notifyDataSetChanged()
    }

}