package com.mistletoe.jobinterview.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.JobInterviewApplication.Companion.repository
import com.mistletoe.jobinterview.data.model.QnA
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {
    val bookmarkedQnAs: StateFlow<List<QnA>> = repository.getBookmarkedQnAs()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQnA(qna: QnA) {
        viewModelScope.launch {
            repository.updateQnA(qna)
        }
    }
}