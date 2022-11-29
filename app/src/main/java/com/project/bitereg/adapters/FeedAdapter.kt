package com.project.bitereg.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.bitereg.R
import com.project.bitereg.databinding.EventItemLayoutBinding
import com.project.bitereg.databinding.NoticeItemLayoutBinding
import com.project.bitereg.databinding.QuoteItemLayoutBinding
import com.project.bitereg.models.*
import com.project.bitereg.utils.ImageLoaderUtils

class FeedAdapter(
    private val posts: MutableList<Post>,
    private val onPostClicked: (Post) -> Unit,
    private val onEventClicked: (Event) -> Unit = onPostClicked
) : RecyclerView.Adapter<FeedAdapter.PostItemViewHolder>() {

    open inner class PostItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    inner class NoticeViewHolder(val binding: NoticeItemLayoutBinding) :
        PostItemViewHolder(binding.root)

    inner class EventViewHolder(val binding: EventItemLayoutBinding) :
        PostItemViewHolder(binding.root)

    inner class QuoteViewHolder(val binding: QuoteItemLayoutBinding) :
        PostItemViewHolder(binding.root)

    private fun inflateView(parent: ViewGroup, resId: Int): View {
        return LayoutInflater.from(parent.context)
            .inflate(resId, parent, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        return when (viewType) {
            TYPE_NOTICE -> NoticeViewHolder(
                NoticeItemLayoutBinding.bind(inflateView(parent, R.layout.notice_item_layout))
            )
            TYPE_QUOTE -> QuoteViewHolder(
                QuoteItemLayoutBinding.bind(inflateView(parent, R.layout.quote_item_layout))
            )
            else -> EventViewHolder(
                EventItemLayoutBinding.bind(inflateView(parent, R.layout.event_item_layout))
            )
        }
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val currentPost = posts[position]
        when (holder) {
            is NoticeViewHolder -> {
                bindNoticeDate(holder.binding, currentPost as Notice)
            }
            is QuoteViewHolder -> {
                bindQuoteData(holder.binding, currentPost as Quote)
            }
            is EventViewHolder -> {
                bindEventData(holder.binding, currentPost as Event)
                holder.binding.registerBtn.setOnClickListener {
                    onEventClicked(currentPost)
                }
            }
        }
        holder.itemView.setOnClickListener {
            onPostClicked(currentPost)
        }
    }

    private fun bindQuoteData(binding: QuoteItemLayoutBinding, quote: Quote) = with(binding) {
        quoteMessage.text = quote.message
        userName.text = quote.creatorName
        Glide.with(this.root).load(quote.creatorProfilePic).placeholder(R.drawable.ic_user)
            .into(userPic)
    }

    private fun bindEventData(binding: EventItemLayoutBinding, event: Event) = with(binding) {
        ImageLoaderUtils.loadFromFirebase(eventImg, event.imageUrl)
        userName.text = event.creatorName
        Glide.with(this.root).load(event.creatorProfilePic).placeholder(R.drawable.ic_user)
            .into(userPic)
    }

    private fun bindNoticeDate(binding: NoticeItemLayoutBinding, notice: Notice) = with(binding) {
        noticeText.text = notice.title
        noticeBody.text = notice.noticeBody
        userName.text = notice.creatorName
        Glide.with(this.root).load(notice.creatorProfilePic).placeholder(R.drawable.ic_user)
            .into(userPic)
    }

    override fun getItemViewType(position: Int): Int {
        val item = posts[position]
        return when (item.type) {
            Notice.TYPE -> TYPE_NOTICE
            Event.TYPE -> TYPE_EVENT
            JobOrIntern.TYPE -> TYPE_JOB_INTERN
            else -> TYPE_QUOTE
        }
    }

    override fun getItemCount(): Int = posts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Post>) {
        posts.clear()
        posts.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_NOTICE = 0
        const val TYPE_EVENT = 1
        const val TYPE_JOB_INTERN = 2
        const val TYPE_QUOTE = 3
    }

}