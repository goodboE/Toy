package com.example.fcam_mediaplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fcam_mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.pauseImageView.setOnClickListener { pauseMediaPlayer() }
        binding.playImageView.setOnClickListener { playMediaPlayer() }
        binding.stopImageView.setOnClickListener { stopMediaPlayer() }
    }

    private fun pauseMediaPlayer() {
        startService(MEDIA_PLAYER_PAUSE)
    }

    private fun playMediaPlayer() {
        startService(MEDIA_PLAYER_PLAY)
    }

    private fun stopMediaPlayer() {
        startService(MEDIA_PLAYER_STOP)
    }

    private fun startService(activity: String) {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply {
                action = activity
            }
        startService(intent)
    }
}