package com.example.dictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val ADD_WORD_STUPID_CODE =  1234  //number between 1-65535

    private var wordToDefn = HashMap<String,String>()
    private val defList = ArrayList<String>()
    private val wordList = ArrayList<String>()


    private lateinit var myAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Zhiqiang", "onCreate was called")

        val reader = Scanner(resources.openRawResource(R.raw.grewords_test))
        readDictionaryFile(reader)  //read the file before setting the list
        val reader2 = Scanner(openFileInput("extrawords.txt"))
        readDictionaryFile(reader2)
        setupList()

        val definitionslist = findViewById<ListView>(R.id.definitions_list)

        definitionslist.setOnItemClickListener { _, _, index, _ ->
            val word = findViewById<TextView>(R.id.the_word).text.toString()
            val defnCorrect = wordToDefn[word]
            val defnChosen = defList[index]
            if (defnChosen == defnCorrect) {
                Toast.makeText(this, "You got it right!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }

            //pick new word and definition
            setupList()
        }

    }

    private fun setupList() {
        //pick a random word
        val rand = Random()
        val index = rand.nextInt(wordList.size)
        val word = wordList[index]
        val theWord = findViewById<TextView>(R.id.the_word)
        theWord.text = word

        defList.clear()
        defList.add(wordToDefn[word]!!)
        wordList.shuffle()
        for (otherWord in wordList.subList(0,5)) {
            if (otherWord == word || defList.size == 5) {
                continue
            }
            defList.add(wordToDefn[otherWord]!!)
        }
        defList.shuffle()

        //attach the list to the ListView using an adapter
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, defList)
        val definitionslist = findViewById<ListView>(R.id.definitions_list)
        definitionslist.adapter = myAdapter
    }

    fun addWordButtonClick(view : View) {
        //launch the AddWordActivity
        val MYIntent = Intent(this, AddWordActivity::class.java)
        MYIntent.putExtra("teacher", "Irina")

        startActivityForResult(MYIntent, ADD_WORD_STUPID_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, MYINTENT: Intent?) {
        super.onActivityResult(requestCode, resultCode, MYINTENT)
        if ( requestCode == ADD_WORD_STUPID_CODE) {

            if (MYINTENT != null) {
                val word = MYINTENT.getStringExtra("word").toString()
                val defn: String = MYINTENT.getStringExtra("defn").toString()
                //wordToDefn[word] = defn
                wordToDefn.put(word, defn)
                wordList.add(word)
            }
        }
    }

    private fun readDictionaryFile(reader: Scanner) {

        while (reader.hasNextLine()) {
            val line = reader.nextLine()
            Log.d("Wang","the line is: $line") //to check in logcat and search "Wang"

            val parts = line.split("\t")   //"\t" is tab
            if (parts.size >= 2)  {
                //not include blank row or only one word
                wordList.add(parts[0])
                wordToDefn.put(parts[0], parts[1])
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.i("Zhiqiang", "onStart was called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("Zhiqiang", "onSaveInstanceState1 was called")
        val word = findViewById<TextView>(R.id.the_word).text.toString()
        outState.putString("the_word", word)
        outState.putStringArrayList("defns", defList)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i("Zhiqiang", "onSaveInstanceState2 was called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Zhiqiang", "onRestoreInstanceState1 was called")
        val word = savedInstanceState.getString("the_word")
        findViewById<TextView>(R.id.the_word).text = word
        val defList = savedInstanceState.getStringArrayList("defns")
        this.defList.clear()
        if (defList != null) {
            for(defn in defList) {
                this.defList.add(defn)
            }
        }
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.defList)
        val definitionslist = findViewById<ListView>(R.id.definitions_list)
        definitionslist.adapter = myAdapter
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.i("Zhiqiang", "onRestoreInstanceState2 was called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Zhiqiang", "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Zhiqiang", "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Zhiqiang", "onStop was called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Zhiqiang", "onRestart was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Zhiqiang", "onDestroy was called")
    }
}