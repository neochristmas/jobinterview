package com.mistletoe.jobinterview.data

import com.mistletoe.jobinterview.data.database.QnADao
import com.mistletoe.jobinterview.data.model.QnA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeQnADao : QnADao {
    private val qnaList = mutableListOf<QnA>() // 데이터 저장소
    private val qnaFlow = MutableStateFlow<List<QnA>>(emptyList()) // 데이터 변화을 비동기적으로 감지하는 구독자

    override suspend fun createQnA(qna: QnA) {
        qnaList.add(qna)
        qnaFlow.value = qnaList.toList()
    }

    override fun getAllQnAs(): Flow<List<QnA>> = qnaFlow

    override suspend fun updateQnA(qna: QnA) {
        val index = qnaList.indexOfFirst { it.qnaId == qna.qnaId }
        if (index != -1) {
            qnaList[index] = qna
            qnaFlow.value = qnaList.toList()
        }
    }

    override suspend fun deleteQnA(qna: QnA) {
        qnaList.removeIf { it.qnaId == qna.qnaId }
        qnaFlow.value = qnaList.toList()
    }

    override fun getBookmarkedQnAs(): Flow<List<QnA>> {
        return qnaFlow.map { list -> list.filter { it.isBookmarked } }
    }
}