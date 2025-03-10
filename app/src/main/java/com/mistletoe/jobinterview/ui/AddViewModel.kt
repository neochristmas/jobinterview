package com.mistletoe.jobinterview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.JobInterviewApplication
import com.mistletoe.jobinterview.data.model.QnA
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    private val repository = JobInterviewApplication.repository

    fun createQnA(tag: String, question: String, answer: String) {
        val qna = QnA(
            tag = tag,
            question = question,
            answer = answer
        )

        viewModelScope.launch {
            repository.createQnA(qna)
        }
    }
}