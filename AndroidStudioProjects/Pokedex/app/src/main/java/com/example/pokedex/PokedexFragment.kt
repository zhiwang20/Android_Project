package com.example.pokedex


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment


class PokedexFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokedex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val table_layout = requireView().findViewById<TableLayout>(R.id.table_layout)

        for (i in 0 until table_layout.childCount) {
            val row = table_layout.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val button = row.getChildAt(j) as ImageButton

                //attach onClick listener to each ImageButton
                button.setOnClickListener{
                    pokemonClick(it)
                }
            }
        }
    }

    fun pokemonClick(view: View) {
        val button: ImageButton = view as ImageButton
        val tag: String = button.tag.toString()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val myIntent = Intent(activity, DetailActivity::class.java)
            myIntent.putExtra("pokemon_name", tag)
            startActivity(myIntent)
        } else {
            val detailsFragment = requireFragmentManager().findFragmentById(R.id.details_fragment) as DetailsFragment
            detailsFragment.displayPokemon(tag)
        }
    }

}
