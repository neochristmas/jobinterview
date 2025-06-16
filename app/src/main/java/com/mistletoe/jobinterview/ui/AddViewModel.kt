package com.mistletoe.jobinterview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.jobinterview.data.QnARepository
import com.mistletoe.jobinterview.data.model.QnA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: QnARepository
) : ViewModel() {

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