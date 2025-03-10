package com.mistletoe.jobinterview.qna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.JobInterviewApplication
import com.mistletoe.jobinterview.data.model.QnA
import kotlinx.coroutines.launch

class QnAListViewModel : ViewModel() {

    private val repository = JobInterviewApplication.repository

    suspend fun fetchQnAs(): List<QnA> {
        val qnaList = repository.getQnAList()
        return qnaList
    }

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
}