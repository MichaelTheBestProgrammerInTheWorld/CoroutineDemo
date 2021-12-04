package com.michaelmagdy.coroutinedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        delay(5000L)
        makeNetworkCall()
        Suspend function 'delay' should be called only from a coroutine or another suspend function
         */

        GlobalScope.launch {
            /*
            delay(5000L)
            Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
            Log.d(TAG, makeNetworkCall())
            val myNet2Result = makeNetworkCall2()
            //delay in both fun will be appended to each other if they're followed by each other
            Log.d(TAG, makeNetworkCall())
            Log.d(TAG, myNet2Result)
            //Log.d(TAG, makeNetworkCall2())
             */
        }
//switch threads of the same coroutine
//        GlobalScope.launch(Dispatchers.IO) {
//            val answer = makeNetworkCall()
//            Log.d(TAG, "Starting coroutine in thread ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main) {
//                text_view.text = answer
//                Log.d(TAG, "Setting text in thread ${Thread.currentThread().name}")
//            }
//        }

        //blocking the main thread 1. only affects main thread 2. similar to Thread.sleep(5000L) outside suspend fun
        Log.d(TAG, "Before runblocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO coroutine 1")
            }
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO coroutine 2")
            }
            Log.d(TAG, "start runblocking")
            delay(5000L)
            Log.d(TAG, "end runblocking")
        }
        Log.d(TAG, "After runblocking")

        button.setOnClickListener {
            lifecycleScope.launch {
                while (true){
                    delay(1000L)
                    Log.d(TAG, "Still running")
                }
            }
            GlobalScope.launch {
                delay(5000L)
                Intent(this@MainActivity, SecondActivity::class.java)
                    .also {
                        startActivity(it)
                        finish()
                    }
            }
        }

        //Log.d(TAG, "Hello from thread ${Thread.currentThread().name}")
    }

    suspend fun makeNetworkCall(): String {

        delay(3000L)
        return "Network call is finished successfully"
    }

    suspend fun makeNetworkCall2(): String {

        delay(3000L)
        return "Network call 2 is finished successfully"
    }
}