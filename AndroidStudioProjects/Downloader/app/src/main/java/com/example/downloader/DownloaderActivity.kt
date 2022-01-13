package com.example.downloader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.koushikdutta.ion.Ion
import java.io.File


class DownloaderActivity : AppCompatActivity() {

    private var files : List<String> = ArrayList()
    private val downloadReceiver = DownloadReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloader)

        if(intent != null){
            val url = intent.getStringExtra("url")
            Toast.makeText(this,"$url down completed",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Activity started normally",Toast.LENGTH_SHORT).show()
        }

        val filter = IntentFilter()
        filter.addAction("downloadcomplete")

        registerReceiver(downloadReceiver, filter)

        findViewById<ListView>(R.id.list_of_links).setOnItemClickListener{
            _, _, index, _ ->
            val fileName = files[index]

            val folder = File(findViewById<EditText>(R.id.the_url).text.toString()).parent

            downloadURL("$folder/$fileName")
        }
    }

    override fun onDestroy() {
        unregisterReceiver(downloadReceiver)
        super.onDestroy()
    }

    fun getListClick(view: View){
        val webPageURL = findViewById<EditText>(R.id.the_url).text.toString()
        Ion.with(this)
            .load(webPageURL)
            .asString()
            .setCallback{
                _, text ->
                files = text.split("\n")
                findViewById<ListView>(R.id.list_of_links).adapter = ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,files.toTypedArray()) as ListAdapter?
            }
    }

    private fun downloadURL(url: String){
        val fake = findViewById<CheckBox>(R.id.fakebox).isChecked
        val delayMS = Integer.valueOf(findViewById<EditText>(R.id.delayedit).text.toString())
        Toast.makeText(this, "Downloading $url ...", Toast.LENGTH_SHORT).show()
        Log.d("DownloaderActivity", "Initiate download of $url ...")

        val intent = Intent(this, DownloadService::class.java)
        intent.action = "download"
        intent.putExtra("url", url)
        startService(intent)

    }

    private inner class DownloadReceiver : BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent) {
            //TODO
            val url = intent.getStringExtra("url")
            Log.d("DownloadReceiver", "this url is done: $url")
            Toast.makeText(this@DownloaderActivity, "done downloading $url", Toast.LENGTH_SHORT).show()
        }
    }



}
