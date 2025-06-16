package com.mistletoe.jobinterview.qna

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class QnAListViewModel @Inject constructor(
    private val repository: QnARepository
) : ViewModel() {

    val qnaList: StateFlow<List<QnA>> = repository.getQnAList()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    var categoryQnaList by mutableStateOf<List<QnA>>(emptyList())
        private set

    fun updateQnA(qna: QnA) {
        viewModelScope.launch {
            repository.updateQnA(qna)
        }
    }

    fun deleteQnA(qna: QnA) {
        viewModelScope.launch {
            repository.deleteQnA(qna)
        }
    }

    fun setQnAList(list: List<QnA>) {
        categoryQnaList = list
    }

    fun getQnAList(): List<QnA> {
        return categoryQnaList
    }
}