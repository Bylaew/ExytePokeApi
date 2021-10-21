package com.bylaew.exytepokeapi.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.model.PokemonInfo.PokemonInfo
import com.bylaew.exytepokeapi.utils.Resource
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {
    val viewModel: InfoViewModel by viewModels()
    private var aboutPokemon: PokemonInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservables()

    }

    private fun initObservables () {
        viewModel.pokemonAbout.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { pokemonResponse ->
                        aboutPokemon = pokemonResponse
                        renderAbout()
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }

        })
    }

    private fun renderAbout() {
        if(aboutPokemon !== null) {
            val descriptionText = aboutPokemon!!.flavor_text_entries[0].flavor_text.replace("\\s+".toRegex()," ")
            tvDescription.text = descriptionText;
        }

    }

}