package com.example.rememberme

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    suspend fun fetchData(): String {
        delay(10000L)
        return "wake up"
    }


    //TODO: use runTest when project is updated
    @Test
    fun testSuspendFunction_fetchData() = runBlockingTest {
        val data = fetchData()
        assertEquals("wake up", data)
    }

}