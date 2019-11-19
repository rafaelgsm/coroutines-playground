package rafaelgsm.example.coroutines_playground

import android.util.Log
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test

class CoroutinesTest {

    class CoroutinesPlayground {

        private val job = Job()
        private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

        suspend fun sum(a: Int, b: Int): Int {
            delay(5_000)
            return a + b
        }
    }

    @Test
    fun sumTest() = runBlocking {

        val c = CoroutinesPlayground()
        assertEquals(4, c.sum(2, 2))
    }
}


