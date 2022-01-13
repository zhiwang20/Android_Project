package com.example.downloader

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi


class DownloadService : Service() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO
        if (intent != null) {
            if (intent.action == "download") {
                val url = intent.getStringExtra("url")
                Log.d("DownloadService", "They want me to download this url: $url ")

                val thread = Thread{
                    if (url != null) {
                        doWork(url)
                    }
                }
                thread.start()

            } else if(intent.action == "bark"){
                Log.d("DownloadService", "ruff")
            }
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun doWork(url:String){
        Downloader().downloadFake(url)

        makeNotification(url)

        val doneIntent = Intent()
        doneIntent.action = "downloadcomplete"
        doneIntent.putExtra("url",url)
        sendBroadcast(doneIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotification(url: String){

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NOTIFICATION_SERVICE)
                            as NotificationManager
            manager.createNotificationChannel(channel)

            val builder = Notification.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Download is done")
                .setContentText(url)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_people)

            val intent = Intent(this,DownloaderActivity::class.java)
            intent.action = "downloadcomplete"
            intent.putExtra("url",url)
            val pending = PendingIntent.getActivity(this,0,intent,0)
            builder.setContentIntent(pending)

            val notification = builder.build()
            manager.notify(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO("Return the communication channel to the service.")
        return null
    }


    companion object{
        private const val NOTIFICATION_CHANNEL_ID = "DownloadService"
        private const val NOTIFICATION_ID = 1234
    }


}
