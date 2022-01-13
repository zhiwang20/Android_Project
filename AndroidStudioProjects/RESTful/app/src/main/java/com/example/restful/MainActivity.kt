package com.example.restful

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getJokeClicked(view: View){
        Ion.with(this)
            .load("http://api.icndb.com/jokes/random")
            .asString()
            .setCallback {
                    _, result ->
                Log.d("Tag","The JSON Data is here:\n $result")
                proccesJokeJSONData(result)
            }

        YoYo.with(Techniques.Tada)
            .duration(700)
            .repeat(5)
            .playOn(findViewById(R.id.getJoke))
    }

    private fun proccesJokeJSONData(result: String){
        val json = JSONObject(result)
        val obj = json.getJSONObject("value")
        val joke = obj.getString("joke")
        findViewById<TextView>(R.id.textView_joke).text = joke
    }


    // get cat Button
	//Ion with Cat API: https://docs.thecatapi.com/
    fun getRandomCatClicked(view: View){
        Ion.with(this)
            .load("https://api.thecatapi.com/api/images/get?format=json&size=med&results_per_page=6")
            .asString()
            .setCallback {
                    _, result ->
                Log.d("Tag","The JSON Data is here:\n $result")
                proccesCatJSONData(result)
                Log.d("Tag","Load Success!")
            }
    }
	

    private fun proccesCatJSONData(result: String){


        val array = JSONObject("{\"images\":$result}")
                        .getJSONArray("images")

        findViewById<GridLayout>(R.id.grid).removeAllViews()
        for (i in 0 until (array.length()-1)) {
            val url = array.getJSONObject(i).getString("url")
            val imageView = ImageView(this)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            findViewById<GridLayout>(R.id.grid).addView(imageView)
            Picasso.get()
                .load(url)
                .into(imageView)
        }

    }
}
