package com.mistletoe.jobinterview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.JobInterviewApplication
import com.mistletoe.jobinterview.data.QnA
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    private val qnaDao = JobInterviewApplication.qnaDao

    fun createQnA(tag: String, question: String, answer: String) {
        val qna = QnA(
            tag = tag,
            question = question,
            answer = answer
        )

        viewModelScope.launch {
            qnaDao.createQnA(qna)
        }
    }
}