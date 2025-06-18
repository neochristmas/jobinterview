package com.mistletoe.jobinterview.qna

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistletoe.GetQnAsQuery
import com.mistletoe.jobinterview.data.ApolloInstance.apolloClient
import com.mistletoe.jobinterview.data.QnARepository
import com.mistletoe.jobinterview.data.model.QnA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _qnas = MutableStateFlow<List<QnA>>(emptyList())
    val qnas: StateFlow<List<QnA>> = _qnas

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

    fun fetchQnAs() {
        viewModelScope.launch {
            try {
                val response = apolloClient.query(GetQnAsQuery()).execute()
                val qnaList = response.data?.qnas?.mapNotNull { qna ->
                    qna?.let {
                        QnA(
                            qnaId = Integer.parseInt(it.qna_id),
                            tag = it.tag,
                            question = it.question,
                            answer = it.answer,
                            isBookmarked = it.is_bookmarked
                        )
                    }
                } ?: emptyList()

                _qnas.value = qnaList
                Log.d("QnAViewModel", "Fetched: $qnaList")

            } catch (e: Exception) {
                Log.e("QnAViewModel", "Error fetching qnas", e)
            }
        }
    }
}