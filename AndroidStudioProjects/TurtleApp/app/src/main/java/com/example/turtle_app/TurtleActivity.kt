package com.example.turtle_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast


class TurtleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterList = findViewById<ListView>(R.id.characterList)

        characterList.setOnItemClickListener { _, _, index, _ ->
                val id = when (index) {
                    0 -> R.drawable.tmntraph
                    1 -> R.drawable.tmntmike
                    2 -> R.drawable.tmntdon
                    else -> R.drawable.tmntleo
            }
            val turtle = findViewById<ImageView>(R.id.turtleImage)
            turtle.setImageResource(id)
        }

    }

    fun radioClick(view : View) {
        val id = when (view.id) {
                R.id.raph_button -> R.drawable.tmntraph
                R.id.mike_button -> R.drawable.tmntmike
                R.id.don_button -> R.drawable.tmntdon
                else -> R.drawable.tmntleo
        }
        val turtle = findViewById<ImageView>(R.id.turtleImage)
        turtle.setImageResource(id)
    }


}