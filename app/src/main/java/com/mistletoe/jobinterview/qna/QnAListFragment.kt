package com.mistletoe.jobinterview.qna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mistletoe.jobinterview.data.QnA
import com.mistletoe.jobinterview.databinding.FragmentQnalistBinding
import com.mistletoe.jobinterview.ui.AddActivity
import kotlinx.coroutines.launch

class QnAListFragment : Fragment(), QnAListAdapter.AddItemClickListener {

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
                "Android" to qnaList.filter { it.tag == "android" },
            )

            adapter = QnAListAdapter(
                requireContext(),
                parentList,
                childList,
                binding,
                this@QnAListFragment
            )
            binding.expandCategory.setAdapter(adapter)
        }

    }

    override fun moveAddScreen(category: String) {
        Log.d("IJ", "Move to Add Screen...")
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}