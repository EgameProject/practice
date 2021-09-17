package com.example.coroutin

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var startTime : Long = 0
    private var endTime : Long = 0
    private var resultTime :Long = 0
    private var flag : Boolean = false
    @RequiresApi(Build.VERSION_CODES.N)
    private val dataFormat: SimpleDateFormat = SimpleDateFormat("mm:ss.SS", Locale.JAPAN)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StartBtn.setOnClickListener() {
            if (!flag) {
                startTime = System.currentTimeMillis();
                //start(startTime)
                flag = true
                GlobalScope.launch() {
                    while (flag) {
                        val text = GlobalScope.async {
                            delay(60)
                            endTime = System.currentTimeMillis()
                            resultTime = endTime - startTime
                            resultTime
                        }

                        timerText.text = dataFormat.format(text.await())
                    }
                }
            }
        }


        StopBtn.setOnClickListener(){
            flag = false

        }

        LisetBtn.setOnClickListener(){
            timerText.text = dataFormat.format(0)
        }
    }

    /*@RequiresApi(Build.VERSION_CODES.N)
    fun start(startTime : Long){
         val text = GlobalScope.async {
             endTime = System.currentTimeMillis()
             resultTime = startTime - endTime
        }

        timerText.text = dataFormat.format(text.await())

    }startという関数の中で行うと、awaitなどにアクセスできない。suspend関数からじゃないとアクセスできないから
    OnCreateの方でlaunchの中で書くことにした
    suspend修飾子をstart関数につければできる*/
}