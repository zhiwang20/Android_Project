package com.example.jukebox

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder

class JukeboxService : Service() {

    companion object {
        const val PLAY_ACTION = "Play"
        const val PLAY_INDEX_ACTION = "PlayIndex"
        const val NEXT_TRACK_ACTION = "Next"
        const val STOP_ACTION = "Stop"

        const val JUKEBOX_NOTIFICATION_ID = 0x193a
        const val JUKEBOX_NOTIFICATION_NAME = "CS193aJukebox"
    }

    private lateinit var songs: Array<String>
    private var media: MediaPlayer? = null
    private var index = -1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        songs = resources.getStringArray(R.array.song_titles)

        makePlayerNotification()

        when {
            intent?.action == PLAY_INDEX_ACTION -> {
                val index = intent.getIntExtra("index", 0)
                playSongAtItem(index)
            }
            intent?.action == PLAY_ACTION -> playNextSong()
            intent?.action == NEXT_TRACK_ACTION -> playNextSong()
            intent?.action == STOP_ACTION -> stopSong()
        }
        return START_STICKY
    }


    private fun makePlayerNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(JUKEBOX_NOTIFICATION_NAME,JUKEBOX_NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            manager.createNotificationChannel(channel)


            val stopIntent = Intent(this, JukeboxService::class.java)
            stopIntent.action = STOP_ACTION
            val pending = PendingIntent.getService(this, 0, stopIntent, 0)
            val stopBmp = BitmapFactory.decodeResource(resources, R.drawable.stop)
            val stopIcon = Icon.createWithBitmap(stopBmp)
            val stopAction = Notification.Action.Builder(
                stopIcon,
                "Stop",
                pending
            ).build()

            val builder = Notification.Builder(this,JUKEBOX_NOTIFICATION_NAME)
            builder.setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.music)
                .addAction(stopAction)

            val notification = builder.build()
            manager.notify(JUKEBOX_NOTIFICATION_ID, notification)

        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun playSongAtItem(index: Int) {
        stopSong()
        this.index = index % songs.size
        val songName = songs[this.index].toLowerCase().replace(" ", "")
        val resID = resources.getIdentifier(songName, "raw", packageName)
        if (resID <= 0)
            return
        media = MediaPlayer.create(this, resID)
        if (media == null)
            return
        media?.setOnCompletionListener {
            onSongComplete()
        }
        media?.start()
    }
    private fun onSongComplete() {
        if (media != null)
            playNextSong()
    }
    private fun playNextSong() {
        index = (index + 1) % songs.size
        playSongAtItem(index)
    }

    private fun stopSong() {
        if (media != null) {
            media?.stop()
            media = null
        }
    }

}
