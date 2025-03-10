package com.mistletoe.jobinterview.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mistletoe.jobinterview.data.model.QnA
import com.mistletoe.jobinterview.databinding.FragmentBookmarkBinding
import com.mistletoe.jobinterview.qna.QnAListViewModel
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment(), BookmarkAdapter.BookmarkClickListener {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: QnAListViewModel by viewModels()
    private val adapter = BookmarkAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerBookmarks.adapter = adapter
        fetchBookmarkedQnAList()
    }

    private fun fetchBookmarkedQnAList() {
        lifecycleScope.launch {
            val qnaList = viewModel.fetchQnAs()
            adapter.setQnAList(qnaList)
        }
    }

    override fun onBookmarkUpdated(qna: QnA) {
        viewModel.updateQnA(qna)
        // TODO 두번 클릭해야 사라짐...
        fetchBookmarkedQnAList()
    }
}