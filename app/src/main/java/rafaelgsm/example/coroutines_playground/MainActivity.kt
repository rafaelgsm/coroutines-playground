package rafaelgsm.example.coroutines_playground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val LOG = "TEST-COROUTINES"

class MainActivity : AppCompatActivity() {

    //region coroutines
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        //region view stuff
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //endregion view stuff

        //region Coroutines Beginning

        //Launch cpu intensive task:
//        coroutineScope.launch {
//
//            //Running a network or file operation:
//            val text = withContext(Dispatchers.IO) {
//                delay(5_000)
//                "here!"
//            }
//
//            //Executing this on Main thread:
//            tv_hello.text = text
//        }

        //endregion

        //--------------------------------------------------------------------------------------

        //region WorkManager

        //In the Activity...
//        val request = OneTimeWorkRequestBuilder<WorkerSum>()                        //
//            .setInputData(workDataOf("x" to 84, "y" to 12))
//            .build()
//
//        WorkManager.getInstance(this).run {
//            enqueue(request)
//            getWorkInfoByIdLiveData(request.id)
//                .observe(this@MainActivity, Observer {
//
//                    //This will trigger twice before executing the actual operation!!!
//
//                    if (it.state == WorkInfo.State.SUCCEEDED) {
//                        val result = it.outputData.getInt("result", 0)
//                        tv_hello.text = "Result is: $result"
//                    }
//                })
//        }   //end WorkManager

        //endregion

        //--------------------------------------------------------------------------------------

        //region Coroutines Advanced


        //Job + Dispatchers.Main
        MainScope().launch {

            //region launch - fire and forget

//            val time = measureTimeMillis {
//
//                val first = withContext(Dispatchers.IO) { delay(1_000); 1 }
//                val second = withContext(Dispatchers.IO) { delay(1_000); 2 }
//
//                Log.d(LOG, "launch results: $first and $second")
//            }
//
//            Log.d(LOG, "elapsed time for launch: $time")
//
//            //endregion

            //--------------------------------------------------------------------------------------

            //region async - deferred

//            val timeAsync = measureTimeMillis {
//
//                val first = async(Dispatchers.IO) { delay(1_000); 1 }
//                val second = async(Dispatchers.IO) { delay(1_000); 2 }
//
//                //Await will wait for the execution to finish and return the value
//                val firstResult = first.await()
//                val secondResult = second.await()
//
//                Log.d(LOG, "async results $firstResult and $secondResult")
//            }
//
//            Log.d(LOG, "elapsed time for async: $timeAsync")

            //endregion

            //--------------------------------------------------------------------------------------

            //region Exceptions

            //...

            //region supervisorScope - coroutineScope
            // A failure of a child does not cause this scope to fail!

//            supervisorScope {
//
//                try {
//
//                    //Exception will happen here!
//                    val result = coroutineScope { throw Exception("Error!"); 123 }
//
//                    Log.d(LOG, "$result")
//
//                } catch (e: Throwable) {
//                    Log.d(LOG, "$e")
//                }
//            }
            //endregion supervisorScope

            //...

            //region supervisorScope - async
            // A failure of a child does not cause this scope to fail!

//            supervisorScope {
//
//                val task = async { throw Exception("Error!"); 123 }
//
//                try {
//
//                    //Exception will happen here!
//                    val result = task.await()
//
//                    Log.d(LOG, "$result")
//
//                } catch (e: Throwable) {
//                    Log.d(LOG, "$e")
//                }
//            }
            //endregion supervisorScope

            //...

            //endregion Exceptions

            //--------------------------------------------------------------------------------------

            //region withTimeout
//            supervisorScope {
//                //...launch...supervisorScope {...
//                try {
//
//                    //Exception will happen here! - change the time to see diff outcomes
//                    val result = withTimeout(1_000) {
//
//                        val first = async(Dispatchers.IO) {
//                            delay(1_500);
//                            Log.d(LOG, "first done!")
//                            1
//                        }
//
//                        val second = async(Dispatchers.IO) {
//                            delay(1_000);
//                            Log.d(LOG, "second done!")
//                            2
//                        }
//
//                        //Await will wait for the execution to finish and return the value
//                        val firstResult = first.await()
//                        val secondResult = second.await()
//
//                        firstResult + secondResult
//                    }
//
//                    Log.d(LOG, "withTimeout result $result")
//
//                } catch (e: TimeoutCancellationException) {
//                    Log.d(LOG, "Time has ended! $e")
//                }
//            }
            //endregion withTimeout

            //--------------------------------------------------------------------------------------

            //region withTimeoutOrNull
//            supervisorScope {
//
//                val longTask = async {
//                    delay(1_500);
//                    Log.d(LOG, "Long task done!")
//                    1
//                }
//
//                //Exception will happen here! - change the time to see diff outcomes
//                val result = withTimeoutOrNull(1_000) {
//
//                    longTask.await()
//                }
//
//                Log.d(LOG, "withTimeoutOrNull result: $result")
//            }
            //endregion withTimeoutOrNull

            //--------------------------------------------------------------------------------------
        }


        //endregion Coroutines Advanced

        //--------------------------------------------------------------------------------------

        //region CallbacksTest
//        MainScope().launch {
//
//            //Callback:
//            LocationManager.getPosition {
//                //Do something
//                Log.d(LOG, "Position retrieved: $it")
//            }
//
//            //With coroutine:
//            val pos = LocationManager.getPositionUpdated()
//            Log.d(LOG, "Position retrieved: $pos")
//        }
        //endregion CallbacksTest

        //--------------------------------------------------------------------------------------

        //region supervisorScope Test
        MainScope().launch {

            val result = supervisorScope {

                try {

                    val x = async {

                        Log.d(LOG, "x runs")

//                        throw Exception()

                        123
                    }

                    val y = async {
                        delay(1_000)
                        Log.d(LOG, "y runs")

                        321
                    }

                    x.await() + y.await()

                } catch (e: Throwable) {
                    Log.d(LOG, "Error! ${e}")

                    0
                }

            } // end supervisorScope

            Log.d(LOG, "SupervisoScope result: $result")

            //....
        }
        //endregion supervisorScope Test

    }

}

//region Callbacks

//Mock class that has some callback that we want to convert:
object LocationManager {
    fun getPosition(callback: (Long) -> Unit) {
        callback(123123)
    }

    suspend fun getPositionUpdated(): Long {
        return suspendCoroutine { continuation ->

            getPosition { pos ->

                continuation.resume(pos)
//                continuation.resumeWithException(Exception("Error!"))
            }

        } //end suspendCoroutine
    }
}   //End class LocationManager

//endregion Callbacks


