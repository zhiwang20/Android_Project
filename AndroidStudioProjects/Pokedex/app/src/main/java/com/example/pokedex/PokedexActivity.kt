package com.example.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PokedexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)
    }

/*
    //check PokedexFragment.kt file's pokemonClick function

    //called when each Pokemon button is clicked; displays details about that pokemon
    fun pokemonClick(view: View) {
        val button: ImageButton = view as ImageButton
        val tag: String = button.tag.toString()

        // jump to DetailsActivity
        val myIntent = Intent(this, DetailActivity::class.java)
        myIntent.putExtra("pokemon_name", tag)
        startActivity(myIntent)
    }
*/
}
