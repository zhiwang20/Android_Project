package com.example.bouncingball


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun moveClicked(view: View) {
        val t = Thread{
            animationLoop()
        }
        t.start()
    }
    private fun animationLoop() {
        while (true) {
            findViewById<MyCanvas>(R.id.myView).moveBall()
            Thread.sleep(50)
        }
    }


}
