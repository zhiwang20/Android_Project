package com.example.puppypower

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.beardedhen.androidbootstrap.BootstrapButton

import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    companion object {
        val WEBSITE = "https://www.martystepp.com/dogs"
        val LIST_OF_FILE = "files.txt"
    }

    private lateinit var allImageName : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Ion.with(this)
            .load("$WEBSITE/$LIST_OF_FILE")
            .asString()
            .setCallback{
                    _, result ->
                allImageName = result.split("\n")
                findViewById<Spinner>(R.id.puppy_spinner).adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allImageName)

                findViewById<BootstrapButton>(R.id.show_button).isEnabled = true
            }
        Log.d("Wang","-----------------------------")
    }

    fun onClickShow(view: View) {
        val index = findViewById<Spinner>(R.id.puppy_spinner).selectedItemPosition
        val pup = allImageName[index]

        Picasso.get()
            .load("$WEBSITE/$pup")
            .resize(600, 600)
            .into(findViewById<ImageView>(R.id.photo_image))

        YoYo.with(Techniques.Tada)
            .duration(700)
            .repeat(5)
            .playOn(findViewById<BootstrapButton>(R.id.show_button))

    }

}
