package com.example.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
/*
        //check DetailsFragment.kt file's displayPokemon function

        if(intent != null) {
            // load the image/text/file data about the Pokemon
            val pokemonName = intent.getStringExtra("pokemon_name") ?: "Pikachu"   //if there isn't one then give me one named "Pikachu"
            val imageID = resources.getIdentifier(pokemonName.toLowerCase(), "drawable",packageName)
            val textFileID = resources.getIdentifier(pokemonName.toLowerCase(), "raw", packageName)
            val fileText = resources.openRawResource(textFileID).bufferedReader().readText()

            val pokemon_name = findViewById<TextView>(R.id.pokemon_name)
            val pokemon_image = findViewById<ImageView>(R.id.pokemon_image)
            val pokemon_detail = findViewById<TextView>(R.id.pokemon_detail)
            pokemon_name.text = pokemonName
            pokemon_image.setImageResource(imageID)
            pokemon_detail.text = fileText
        }
*/
    }
}
