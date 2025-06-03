package com.mistletoe.jobinterview

import android.app.Application
import com.mistletoe.jobinterview.data.QnARepository
import com.mistletoe.jobinterview.data.database.QnADatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JobInterviewApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = QnADatabase.getDatabase(this)
        val qnaDao = database.getQnADao()
        repository = QnARepository(qnaDao)
    }

    companion object {
        lateinit var repository: QnARepository
    }
}