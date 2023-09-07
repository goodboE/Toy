package com.example.fcam_mediaplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val receiver = LowBatteryReceiver()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        initReceiver()

        val playIcon = Icon.createWithResource(baseContext, R.drawable.ic_baseline_play_arrow_24)
        val pauseIcon = Icon.createWithResource(baseContext, R.drawable.ic_baseline_pause_24)
        val stopIcon = Icon.createWithResource(baseContext, R.drawable.ic_baseline_stop_24)

        val pausePendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )

        val playPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(baseContext, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                },
            PendingIntent.FLAG_IMMUTABLE
        )


        val notification = Notification.Builder(baseContext, CHANNEL_ID)
            .setStyle(
                Notification.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_baseline_star_24)
            .addAction(
                Notification.Action.Builder(
                    pauseIcon,
                    "PAUSE",
                    pausePendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    playIcon,
                    "PLAY",
                    playPendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    stopIcon,
                    "STOP",
                    stopPendingIntent
                ).build()
            )
            .setContentIntent(mainPendingIntent)
            .setContentTitle("Play Music")
            .setContentText("PLAYING MUSIC...")
            .build()

        startForeground(100, notification)
    }

    override fun onDestroy() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "MEDIA_PLAYER", NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = baseContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun initReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            MEDIA_PLAYER_PLAY -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.music)
                }
                mediaPlayer?.start()
            }
            MEDIA_PLAYER_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                stopSelf()
            }
            MEDIA_PLAYER_PAUSE -> {
                mediaPlayer?.pause()
            }

        }

        return super.onStartCommand(intent, flags, startId)
    }
}