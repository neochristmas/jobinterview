package com.mistletoe.jobinterview.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mistletoe.jobinterview.data.model.QnA

@Dao
interface QnADao {

    @Insert
    suspend fun createQnA(qna: QnA)

    @Query("SELECT * FROM qna")
    suspend fun getAllQnAs(): List<QnA>

    @Update
    suspend fun updateQnA(qna: QnA)

    @Delete
    suspend fun deleteQnA(qna: QnA)
}