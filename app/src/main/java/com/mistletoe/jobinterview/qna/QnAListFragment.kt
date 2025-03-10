package com.mistletoe.jobinterview.qna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mistletoe.jobinterview.data.model.QnA
import com.mistletoe.jobinterview.databinding.FragmentQnalistBinding
import com.mistletoe.jobinterview.ui.AddActivity
import com.mistletoe.jobinterview.ui.PracticeActivity
import kotlinx.coroutines.launch

class QnAListFragment : Fragment(), QnAListAdapter.ItemClickListener {

    private lateinit var binding: FragmentQnalistBinding
    private lateinit var adapter: QnAListAdapter
    private var qnaList: List<QnA> = listOf()
    private val viewModel: QnAListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQnalistBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setQnAList()
//    }

    override fun onResume() {
        super.onResume()
        setQnAList()
    }

    private fun setQnAList() {
        lifecycleScope.launch {
            qnaList = viewModel.fetchQnAs()

            val parentList = listOf("Tell me about yourself", "Android")
            val childList = hashMapOf(
                "Tell me about yourself" to qnaList.filter { it.tag == "Tell me about yourself" },
                "Android" to qnaList.filter { it.tag == "Android" },
            )

            if (!::adapter.isInitialized) {
                adapter = QnAListAdapter(
                    requireContext(),
                    parentList,
                    childList,
                    binding,
                    this@QnAListFragment
                )
                binding.expandCategory.setAdapter(adapter)
            } else {
                adapter.updateData(parentList, childList)
            }

        }

    }

    private fun setListViewHeightBasedOnChildren(listView: ExpandableListView, group: Int) {
//        val listAdapter = listView.expandableListAdapter
//        var totalHeight = 0
//        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
//            listView.width,
//            View.MeasureSpec.EXACTLY
//        )
//        for (i in 0 until listAdapter.groupCount) {
//            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//            totalHeight += groupItem.measuredHeight
//            if (listView.isGroupExpanded(i) && i != group
//                || !listView.isGroupExpanded(i) && i == group
//            ) {
//                for (j in 0 until listAdapter.getChildrenCount(i)) {
//                    val listItem: View = listAdapter.getChildView(
//                        i, j, false, null, listView
//                    )
//                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
//                    totalHeight += listItem.measuredHeight
//                }
//            }
//            val params = listView.layoutParams
//            var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1))
//            if (height < 10) {
//                height = 200
//            }
//            params.height = height
//            listView.layoutParams = params
//            listView.requestLayout()
//        }
        val listAdapter = listView.expandableListAdapter ?: return

        var totalHeight = 0
        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(0, 0)
            totalHeight += groupItem.measuredHeight

            if (listView.isGroupExpanded(i)) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val childItem = listAdapter.getChildView(i, j, false, null, listView)
                    childItem.measure(0, 0)
                    totalHeight += childItem.measuredHeight
                }
            }
        }

        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.groupCount - 1))
        Log.d("IJ","Height is... ${params.height}")
        listView.layoutParams = params
        listView.requestLayout()
    }

    override fun moveToAddScreen(category: String) {
        Log.d("IJ", "Move to Add Screen...")
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    override fun moveToPracticeScreen(category: String) {
        Log.d("IJ", "Move to Practice Screen...")
        val filteredQnAList: ArrayList<QnA> = qnaList.filter { it.tag == category }
            .toCollection(ArrayList())

        val intent = Intent(requireContext(), PracticeActivity::class.java)
        intent.putParcelableArrayListExtra("qna_list", filteredQnAList)
        startActivity(intent)
    }

    override fun onBookmarkUpdated(qna: QnA) {
        Log.d("IJ", "Update bookmark...")
        viewModel.updateQnA(qna)
    }

    override fun onQnADeleted(qna: QnA) {
        Log.d("IJ", "Delete QnA...")

        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Do you really want to delete this QnA?")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteQnA(qna)
                dialog.dismiss()
                setQnAList()
            }
            .show()
    }

    override fun updateScreenHeight(groupPosition: Int) {
        val listAdapter = binding.expandCategory.expandableListAdapter ?: return

        var totalHeight = 0
        val widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(binding.expandCategory.width, View.MeasureSpec.AT_MOST)

        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, binding.expandCategory)
            groupItem.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight

            Log.d("IJ","Group Height is... $totalHeight...GroupIndex is $i")

            if (binding.expandCategory.isGroupExpanded(i)) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val childItem = listAdapter.getChildView(i, j, false, null, binding.expandCategory)
                    childItem.layoutParams = AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    childItem.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED)
                    Log.d("IJ","Child Height is... ${childItem.measuredHeight}...ChildIndex is $j")

                    totalHeight += childItem.measuredHeight / 2
                }
            }
            Log.d("IJ","Children Height is... $totalHeight")

        }


        val params = binding.expandCategory.layoutParams
        params.height = totalHeight + (binding.expandCategory.dividerHeight * (listAdapter.groupCount - 1))
        Log.d("IJ","Height is... ${params.height}")
        binding.expandCategory.layoutParams = params
        binding.expandCategory.requestLayout()
    }
}