// AppModule.kt
package com.mistletoe.jobinterview.di

import android.content.Context
import androidx.room.Room
import com.mistletoe.jobinterview.data.QnARepository
import com.mistletoe.jobinterview.data.database.QnADatabase
import com.mistletoe.jobinterview.data.database.QnADao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): QnADatabase {
        return Room.databaseBuilder(
            appContext,
            QnADatabase::class.java,
            "qna-database"
        ).build()
    }

    @Provides
    fun provideQnADao(database: QnADatabase): QnADao {
        return database.getQnADao()
    }

    @Provides
    @Singleton
    fun provideQnARepository(qnADao: QnADao): QnARepository {
        return QnARepository(qnADao)
    }
}
