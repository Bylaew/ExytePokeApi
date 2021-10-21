package com.bylaew.exytepokeapi.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bylaew.exytepokeapi.MainActivity
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.adapter.ViewPageAdapter
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.utils.Resource
import kotlinx.android.synthetic.main.fragment_stats.*


class StatsFragment : Fragment() {

    val viewModel: InfoViewModel by viewModels()
    private var pokemonDetails: Resource<PokemonById>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pokemonDetails = viewModel.pokemonDetails.value

        renderStats()

    }

    private fun renderStats () {
        val pokeStats = pokemonDetails?.data?.stats;

        if (pokeStats != null) {
            for (stat in pokeStats){

                val typeStat = stat.stat.name
                val valueStat = stat.base_stat;

                when(typeStat){
                    "hp" ->  progressBarHP.progress = valueStat
                    "attack" -> progressBarAttack.progress = valueStat
                    "defense" -> progressBarDefense.progress = valueStat
                    "special-attack" -> progressBarSpAtk.progress = valueStat
                    "special-defense" -> progressBarSpDef.progress = valueStat
                    "speed" -> progressBarSpeed.progress = valueStat
                }
            }
        }
    }
}