package com.mistletoe.jobinterview.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.data.QnARepository
import com.mistletoe.jobinterview.data.model.QnA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: QnARepository
) : ViewModel() {

    val bookmarkList: StateFlow<List<QnA>> = repository.getBookmarkedQnAs()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQnA(qna: QnA) {
        viewModelScope.launch {
            repository.updateQnA(qna)
        }
    }
}