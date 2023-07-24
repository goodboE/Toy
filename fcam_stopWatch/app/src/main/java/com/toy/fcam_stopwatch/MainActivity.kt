package com.toy.fcam_stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.toy.fcam_stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var countdownSecond = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countdownTextView.setOnClickListener {

        }

        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.stopButton.isVisible = false
            binding.pauseButton.isVisible = true
            binding.lapButton.isVisible = true
        }

        binding.stopButton.setOnClickListener {
            showAlertDialog()

        }

        binding.pauseButton.setOnClickListener {
            binding.startButton.isVisible = true
            binding.stopButton.isVisible = true
            binding.pauseButton.isVisible = false
            binding.lapButton.isVisible = false
        }

        binding.lapButton.setOnClickListener {
            lap()
        }



    }

    private fun start() {

    }

    private fun pause() {
        stop()
    }

    private fun stop() {
        binding.startButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = false
        binding.lapButton.isVisible = false
    }

    private fun lap() {

    }

    private fun showCountDownSettingDialog() {
        AlertDialog.Builder(this).apply {
            setPositiveButton("확인") { _, _ ->



            }
            setNegativeButton("취소", null)

        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }

            setNegativeButton("아니요") { _, _ ->

            }

        }.show()

    }


}