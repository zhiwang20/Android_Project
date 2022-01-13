package com.example.dictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.io.PrintStream

class AddWordActivity : AppCompatActivity() {
    private val WORDS_FILE_NAME = "extrawords.txt"   //initialized a new created file

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        //send from MainActivity
        val teacher = intent.getStringExtra("teacher")
        findViewById<EditText>(R.id.word_to_add).setText(teacher)

    }

    fun letsAddTheWord(view: View) {

        val word = findViewById<EditText>(R.id.word_to_add).text.toString()

        val defn = findViewById<EditText>(R.id.word_defintion).text.toString()

        val line = "$word\t$defn"   // ex: apple tab fruit

        val outStream = PrintStream(openFileOutput(WORDS_FILE_NAME, MODE_PRIVATE))
        outStream.println(line)
        outStream.close()

        val myIntent = Intent()
        myIntent.putExtra("word", word)
        myIntent.putExtra("defn", defn)
        setResult(RESULT_OK, myIntent)
        finish()
    }
}