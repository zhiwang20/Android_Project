package com.example.jukebox

import android.content.Intent
import android.os.*
//import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class JukeboxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jukebox)
        findViewById<ListView>(R.id.song_list).setOnItemClickListener { _, _, index, _ ->
            playSongAtIndex(index)
        }
    }
    private fun playSongAtIndex(index: Int) {
        val intent = Intent(this, JukeboxService::class.java)
        intent.action = JukeboxService.PLAY_INDEX_ACTION
        intent.putExtra("index", index)
        startService(intent)
    }

    private fun doIntent(action: String) {
        val intent = Intent(this, JukeboxService::class.java)
        intent.action = action
        startService(intent)
    }

    fun onClickPlay(view: View) {
        doIntent(JukeboxService.PLAY_ACTION)
    }

    fun onClickNextTrack(view: View) {
        doIntent(JukeboxService.NEXT_TRACK_ACTION)
    }

    fun onClickStop(view: View) {
        doIntent(JukeboxService.STOP_ACTION)
    }

}

