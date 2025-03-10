package com.mistletoe.jobinterview.data

import com.mistletoe.jobinterview.data.database.QnADao
import com.mistletoe.jobinterview.data.model.QnA

class QnARepository(private val qnADao: QnADao) {

    suspend fun createQnA(qna: QnA) {
        qnADao.createQnA(qna)
    }

    suspend fun getQnAList(): List<QnA> {
        return qnADao.getAllQnAs()
    }

    suspend fun updateQnA(qna: QnA) {
        qnADao.updateQnA(qna)
    }

    suspend fun deleteQnA(qna: QnA) {
        qnADao.deleteQnA(qna)
    }
}