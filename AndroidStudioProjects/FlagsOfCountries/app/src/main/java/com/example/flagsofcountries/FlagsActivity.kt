package com.example.flagsofcountries

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
//import android.support.v7.app.AlertDialog
//import android.app.AlertDialog
//two type of AlertDialag?
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FlagsActivity : AppCompatActivity() {

    private val COUNTRIES = listOf(
        "China",
        "Egypt",
        "Germany",
        "Ghana",
        "Japan",
        "Spain",
        "United Kingdom"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flags)


        for(country in COUNTRIES) {
            createFlag(country)
        }
    }

    fun createFlag(countryName: String) {
        val flag = layoutInflater.inflate(R.layout.flag, null)
        val image = flag.findViewById<ImageView>(R.id.flag_image)
        image.setOnClickListener{
            Toast.makeText(this,"You Clicked $countryName",Toast.LENGTH_SHORT).show()
            showCountryInfo(countryName)
        }

        val countryNameView = flag.findViewById<TextView>(R.id.country_name)
        val checkbox = flag.findViewById<CheckBox>(R.id.checkBox)

        countryNameView.text = countryName

        val lowerCountryName = countryName.toLowerCase().replace(" ","")
        //turn "United State" into R.drawable.unitedstates
        val imageID = resources.getIdentifier(lowerCountryName, "drawable", packageName)
        image.setImageResource(imageID)

        findViewById<GridLayout>(R.id.gird_of_flags).addView(flag)
    }

    fun showCountryInfo(countryName: String) {
        // FileText
        val countryName2 = countryName.toLowerCase().replace(" ","")
        val textFileID = resources.getIdentifier(countryName2,"raw",packageName)
        val fileText = resources.openRawResource(textFileID).bufferedReader().readText()

        // Music
        val mp3FileID = resources.getIdentifier(countryName2+"_music", "raw", packageName)
        val mp = MediaPlayer.create(this,mp3FileID)
        mp.start()

        // Dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Info About $countryName")
        val message = "Hello World! $countryName \n $fileText"
        builder.setMessage(message)
        builder.setPositiveButton("OK"){
            _, _ ->
            // when click OK , Dialog will be closed
            mp.stop()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
