package com.example.prac_thread_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.prac_thread_timer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var total = 0
    private var started = false
    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total/60)
            val second = String.format("%02d", total%60)
            mBinding.textTimer.text = "$minute:$second"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnStart.setOnClickListener {
            if (!started) {
                started = true
                thread(start = true) {
                    while(started) {
                        Thread.sleep(1000)
                        if (started) {
                            total += 1
                            handler.sendEmptyMessage(0)
                        }
                    }
                }
            }
        }

        mBinding.btnEnd.setOnClickListener {
            if (started) {
                started = false
                total = 0
                mBinding.textTimer.text = "00:00"
            }
        }

    }
}