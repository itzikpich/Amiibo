package com.itzikpich.amiiboapiapp

import com.itzikpich.amiiboapiapp.view_models.MainViewModel
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import kotlin.system.measureTimeMillis

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
}

class MyTest {

    var isDelayed = false

    suspend fun delayMe() {
        withContext(Dispatchers.IO) {
            delay(3000)
            isDelayed = true
        }
    }

    @Test
    fun testData()  {
        GlobalScope.launch {
            val time = measureTimeMillis {
                delayMe()
            }
            assertTrue(isDelayed)
            println(time)
        }
        println("finished")
    }
}