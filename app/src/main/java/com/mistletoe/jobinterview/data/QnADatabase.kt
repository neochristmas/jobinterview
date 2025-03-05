package com.mistletoe.jobinterview.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QnA::class], version = 1)
abstract class QnADatabase : RoomDatabase() {

    abstract fun getQnADao(): QnADao

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: QnADatabase? = null

        fun getDatabase(context: Context): QnADatabase {
            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    QnADatabase::class.java,
                    "qna-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}