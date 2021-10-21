package com.bylaew.exytepokeapi.ui.info

import android.annotation.SuppressLint
import android.icu.text.IDNA
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bylaew.exytepokeapi.MainActivity
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.adapter.ViewPageAdapter
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.utils.Constants
import com.bylaew.exytepokeapi.utils.Resource
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_info.*
import java.util.*


class InfoFragment : Fragment() {

    lateinit var pokemonDetails : PokemonById

    val viewModel: InfoViewModel by viewModels()

    var pokemonId : Int = 0

    private val adapter by lazy { activity?.let { ViewPageAdapter(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pokemonId = requireArguments().getInt("id")

        viewModel.getPokemonById(pokemonId+1)
        viewModel.getDescriptionPokemon(pokemonId+1)

        viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { pokemonResponse ->
                        pokemonDetails = pokemonResponse
                        initUI()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        initViewPagerAdapter()
    }

    private fun hideProgressBar () {
        progressBar.visibility = View.INVISIBLE
        layout.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        layout.visibility = View.INVISIBLE
    }


    private fun initViewPagerAdapter(){
        pager.adapter = adapter

        val tabLayoutMediator =  TabLayoutMediator(tabLayout, pager) { tab, position ->
            when(position){
                0 -> {
                    tab.text ="About"
                }
                1 -> {
                    tab.text ="Stats"
                }
            }
        }
        tabLayoutMediator.attach()
    }


    @SuppressLint("SetTextI18n")
    private fun initUI() {

        tvName.text = pokemonDetails.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        tvNumberPokedex.text = "#${pokemonDetails.id}"
        tvWeight.text = "Weight: ${pokemonDetails.weight}"
        Glide.with(this)
            .load(Constants.BASE_IMG_URL +"${pokemonDetails.id}.png")
            .thumbnail(0.25f)
            .into(ivPokemon)


        for (type in pokemonDetails.types) {
            if(type.slot == 1) tvType1.text = type.type.name
            if(type.slot == 2) tvType2.text = type.type.name
        }

    }

}