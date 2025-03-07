package com.mistletoe.jobinterview.qna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.JobInterviewApplication
import com.mistletoe.jobinterview.data.QnA
import kotlinx.coroutines.launch

class QnAListViewModel : ViewModel() {

    private val qnaDao = JobInterviewApplication.qnaDao

    suspend fun fetchQnAs(): List<QnA> {
        val qnaList = qnaDao.getAllQnAs()
        return qnaList
    }

    fun updateQnA(qna: QnA) {
        viewModelScope.launch {
            qnaDao.updateQnA(qna)
        }
    }

    fun deleteQnA(qna: QnA) {
        viewModelScope.launch {
            qnaDao.deleteQnA(qna)
        }
    }
}