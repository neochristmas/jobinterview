package com.mistletoe.jobinterview.qna

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.mistletoe.jobinterview.R
import com.mistletoe.jobinterview.data.QnA
import com.mistletoe.jobinterview.databinding.FragmentQnalistBinding
import com.mistletoe.jobinterview.databinding.ItemCategoryBinding
import com.mistletoe.jobinterview.databinding.ItemQnaBinding

class QnAListAdapter(
    private val context: Context,
    private val parentList: List<String>,
    private val childList: HashMap<String, List<QnA>>,
    private val qnaListBinding: FragmentQnalistBinding,
    private val listener: AddItemClickListener
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

        binding.textTitle.text = parentList[groupPosition]
        binding.buttonArrow.setOnClickListener {
            Log.d("IJ", "Clicked...? $isExpanded")
            if (isExpanded) {
                binding.buttonArrow.setImageResource(R.drawable.ic_arrow_up)
                qnaListBinding.expandCategory.collapseGroup(groupPosition) // 그룹이 열려있으면 닫기
            } else {
                binding.buttonArrow.setImageResource(R.drawable.ic_arrow_down)
                qnaListBinding.expandCategory.expandGroup(groupPosition) // 그룹이 닫혀있으면 열기
            }
//            setArrow(binding, isExpanded)

        }

        binding.buttonAdd.setOnClickListener {
            listener.moveAddScreen(parentList[groupPosition])
        }

        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflater = LayoutInflater.from(context)
        val binding = ItemQnaBinding.inflate(inflater, parent, false)

        binding.textQuestion.text = getChild(groupPosition, childPosition).question
        binding.textAnswer.text = getChild(groupPosition, childPosition).answer
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    private fun setArrow(binding: ItemCategoryBinding, isExpanded: Boolean) {
        if (isExpanded) {
            binding.buttonArrow.setImageResource(R.drawable.ic_arrow_up)
        } else {
            binding.buttonArrow.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    interface AddItemClickListener {
        fun moveAddScreen(category: String)
    }
}