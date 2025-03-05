package com.mistletoe.jobinterview.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qna")
data class QnA(
    @PrimaryKey(autoGenerate = true) val qnaId: Int = 0,
    val tag: String = "",
    val question: String = "",
    val answer: String = "",
)
