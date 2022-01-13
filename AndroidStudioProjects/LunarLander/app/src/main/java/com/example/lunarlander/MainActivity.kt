package com.example.lunarlander

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun playClick(view: View){
        findViewById<LanderCanvas>(R.id.mycanvas).startGame()
    }

    fun stopClick(view: View){
        findViewById<LanderCanvas>(R.id.mycanvas).stopGame()
    }

    fun replayClick(view: View){
        val intent = intent
        finish()
        startActivity(intent)
    }

}
