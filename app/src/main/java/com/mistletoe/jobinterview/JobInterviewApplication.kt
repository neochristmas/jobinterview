package com.mistletoe.jobinterview

import android.app.Application
import com.mistletoe.jobinterview.data.QnADao
import com.mistletoe.jobinterview.data.QnADatabase

class JobInterviewApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = QnADatabase.getDatabase(this)
        qnaDao = database.getQnADao()
    }

    companion object {
        lateinit var database: QnADatabase
        lateinit var qnaDao: QnADao
    }
}