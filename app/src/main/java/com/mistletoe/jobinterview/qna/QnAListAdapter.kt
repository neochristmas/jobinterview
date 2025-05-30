package com.mistletoe.jobinterview.qna

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.data.model.QnA
import com.mistletoe.jobinterview.databinding.FragmentQnalistBinding
import com.mistletoe.jobinterview.databinding.ItemCategoryBinding
import com.mistletoe.jobinterview.databinding.ItemQnaBinding

class QnAListAdapter(
    private val context: Context,
    private var parentList: List<String>,
    private var childList: HashMap<String, List<QnA>>,
    private val qnaListBinding: FragmentQnalistBinding,
    private val listener: ItemClickListener
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = parentList.size

    override fun getChildrenCount(groupPosition: Int): Int {
        return childList[parentList[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any = parentList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): QnA =
        childList[parentList[groupPosition]]?.get(childPosition) ?: QnA()

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding: ItemCategoryBinding

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            binding = ItemCategoryBinding.inflate(inflater, parent, false)
            binding.root.tag = binding
            Log.d("IJ", "Inflating new view for group: ${parentList[groupPosition]}")
            Log.d("IJ", "isExpanded...? $isExpanded")
        } else {
            binding = convertView.tag as ItemCategoryBinding
            Log.d("IJ", "Reusing view for group: ${parentList[groupPosition]}")
            Log.d("IJ", "isExpanded...? $isExpanded")
        }

        binding.apply {
            textTitle.text = parentList[groupPosition]

            if (isExpanded) {
                buttonArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                buttonArrow.setImageResource(R.drawable.ic_arrow_down)
            }

            buttonArrow.setOnClickListener {
                Log.d("IJ", "Clicked...? $isExpanded")
                if (isExpanded) {
                    qnaListBinding.expandCategory.collapseGroup(groupPosition)
                } else {
                    qnaListBinding.expandCategory.expandGroup(groupPosition)
                }

                buttonArrow.setImageResource(
                    if (isExpanded) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                )

                listener.updateScreenHeight(groupPosition)
            }

            buttonAdd.setOnClickListener {
                listener.moveToAddScreen(parentList[groupPosition])
            }

            buttonPractice.setOnClickListener {
                listener.moveToPracticeScreen(parentList[groupPosition])
            }

            return root
        }
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater = LayoutInflater.from(context)
        ItemQnaBinding.inflate(inflater, parent, false).apply {
            textQuestion.text = getChild(groupPosition, childPosition).question
            textAnswer.text = getChild(groupPosition, childPosition).answer

            toggleBookmark.isChecked = getChild(groupPosition, childPosition).isBookmarked
            toggleBookmark.setOnClickListener {
                Log.d("IJ", "Click bookmark...")
                val updateBookmark = getChild(groupPosition, childPosition).copy(
                    isBookmarked = !getChild(
                        groupPosition,
                        childPosition
                    ).isBookmarked
                )
                listener.onBookmarkUpdated(updateBookmark)
            }

            root.setOnClickListener {
                listener.onQnADeleted(getChild(groupPosition, childPosition))
            }

            return root
        }


    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    fun updateData(newParentList: List<String>, newChildList: HashMap<String, List<QnA>>) {
        parentList = newParentList
        childList = newChildList
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun moveToAddScreen(category: String)

        fun moveToPracticeScreen(category: String)

        fun onBookmarkUpdated(qna: QnA)

        fun onQnADeleted(qna: QnA)

        fun updateScreenHeight(groupPosition: Int)
    }
}