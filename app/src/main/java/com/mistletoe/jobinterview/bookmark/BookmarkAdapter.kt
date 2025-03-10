package com.mistletoe.jobinterview.bookmark

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mistletoe.jobinterview.data.model.QnA
import com.mistletoe.jobinterview.databinding.ItemQnaBinding

class BookmarkAdapter(private val listener: BookmarkClickListener) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    private var qnaList: List<QnA> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemQnaBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = qnaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(qnaList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setQnAList(qnaList: List<QnA>) {
        this.qnaList = qnaList.filter { it.isBookmarked }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemQnaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(qna: QnA) {
            binding.apply {
                textQuestion.text = qna.question
                textAnswer.text = qna.answer
                toggleBookmark.isChecked = qna.isBookmarked

                toggleBookmark.setOnClickListener {
                    val updateBookmark = qna.copy(isBookmarked = toggleBookmark.isChecked)
                    listener.onBookmarkUpdated(updateBookmark)
                }
            }
        }

    }

    interface BookmarkClickListener {
        fun onBookmarkUpdated(qna: QnA)
    }
}