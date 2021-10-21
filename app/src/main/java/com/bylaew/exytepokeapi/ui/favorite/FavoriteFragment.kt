package com.bylaew.exytepokeapi.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bylaew.exytepokeapi.MainActivity
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.adapter.FavouritePokemonAdapter
import kotlinx.android.synthetic.main.favorite_fragment.favourPokemonItems
import kotlinx.android.synthetic.main.favorite_fragment.no_fav_view

class FavoriteFragment : Fragment() {

    private lateinit var pokAdapter: FavouritePokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: FavoriteViewModel by viewModels { FavouriteViewModelFactory(context!!) }
        initAdapter()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.pokAll.observe(viewLifecycleOwner, {
            pokAdapter.differ.submitList(it.toList())
            if (it.isEmpty()) {
                favourPokemonItems.visibility = View.INVISIBLE
                no_fav_view.visibility = View.VISIBLE

            } else {
                favourPokemonItems.visibility = View.VISIBLE
                no_fav_view.visibility = View.INVISIBLE

            }

        })

        pokAdapter.setOnItemClickListener {currentPokemon ->
            val bundle = Bundle().apply {
                putInt("id",currentPokemon)
            }
            findNavController().navigate(
                R.id.action_favoriteFragment_to_infoFragment,
                bundle
            )
        }

    }

    private fun initAdapter() {
        pokAdapter = FavouritePokemonAdapter(requireContext())
        favourPokemonItems.apply {
            adapter = pokAdapter
            layoutManager = GridLayoutManager(context,2)

        }

    }

}