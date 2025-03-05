package com.mistletoe.jobinterview.qna

import androidx.lifecycle.ViewModel
import com.mistletoe.jobinterview.JobInterviewApplication
import com.mistletoe.jobinterview.data.QnA

class QnAListViewModel : ViewModel() {
    private val qnaDao = JobInterviewApplication.qnaDao

    suspend fun fetchQnAs(): List<QnA> {
        val qnaList = qnaDao.getAllQnAs()
        return qnaList
    }
}