package rafaelgsm.example.coroutines_playground

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class WorkerSum(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {

        // Asynchronous work can be done here!

        inputData.run {

            //get "extras" in this scope, for the WorkManager.
            val x = getInt("x", 0)
            val y = getInt("y", 0)
            val result = sum(x, y)

            workDataOf("result" to result)
        }

        Result.success()  //Returns this

    } catch (error: Throwable) {
        Result.failure()    //Or returns this
    }

    //Function that is NOT asynchronous (But will be called from an asynchronous scope)
    suspend fun sum(a: Int, b: Int): Int {
        delay(5_000)
        return a + b
    }
}