package com.mistletoe.jobinterview.data

import com.mistletoe.jobinterview.data.model.QnA
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QnARepositoryTest {

    private lateinit var repository: QnARepository
    private lateinit var fakeQnADao: FakeQnADao

    @Before
    fun setup() {
        fakeQnADao = FakeQnADao()
        repository = QnARepository(fakeQnADao)
    }

    @Test
    fun `createQnA adds item to list`() = runTest {
        val qna = QnA(1, "Kotlin", "What is Kotlin?", "Kotlin is a modern JVM language.")
        repository.createQnA(qna)

        val result = repository.getQnAList().first()
        assertEquals(1, result.size)
        assertEquals("What is Kotlin?", result[0].question)
    }

    @Test
    fun `updateQnA modifies existing item`() = runTest {
        val qna = QnA(1, "Kotlin", "What is Kotlin?", "Kotlin is a modern JVM language.")
        repository.createQnA(qna)

        val updatedQnA = qna.copy(question = "Do you know Kotlin?")
        repository.updateQnA(updatedQnA)

        val result = repository.getQnAList().first()
        assertEquals("Do you know Kotlin?", result[0].question)
    }

    @Test
    fun `deleteQnA removes item`() = runTest {
        val qna = QnA(2, "Android", "What is Android?", "Android is a mobile operating system")
        repository.createQnA(qna)

        repository.deleteQnA(qna)
        val result = repository.getQnAList().first()
        assertTrue(result.isEmpty())
    }


}