package com.example.pokedex


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val act = activity
        if(act?.intent != null) {
            val pokemonName = act.intent.getStringExtra("pokemon_name") ?: "a"
            displayPokemon(pokemonName)
        }
    }

    fun displayPokemon(pokemonName: String) {
        val imageID = resources.getIdentifier(pokemonName.toLowerCase(), "drawable",requireActivity().packageName)
        val textFileID = resources.getIdentifier(pokemonName.toLowerCase(), "raw", requireActivity().packageName)
        val fileText = resources.openRawResource(textFileID).bufferedReader().readText()

        val pokemon_name = requireView().findViewById<TextView>(R.id.pokemon_name)
        val pokemon_image = requireView().findViewById<ImageView>(R.id.pokemon_image)
        val pokemon_detail = requireView().findViewById<TextView>(R.id.pokemon_detail)
        pokemon_name.text = pokemonName
        pokemon_image.setImageResource(imageID)
        pokemon_detail.text = fileText
    }

}
