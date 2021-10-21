package com.bylaew.exytepokeapi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bylaew.exytepokeapi.MainActivity
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.adapter.PokemonAdapter
import com.bylaew.exytepokeapi.utils.Resource
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    val viewModel: MainViewModel by viewModels()
    lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)


        viewModel.pokemonList.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { pokemonResponse ->
                        pokemonAdapter.differ.submitList(pokemonResponse.results.toList())
                        val totalPages = pokemonResponse.count / 10 + 2
                        isLastPage = viewModel.pokemonPages == totalPages
                        if(isLastPage){
                            rvPokemonItems.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(context, "Oops. No connection...", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        }

        viewModel.pokemonDetails.observe(viewLifecycleOwner) {

            if(it.data?.name != null) {
                GlobalScope.launch { viewModel.putLike(requireContext(),it.data) }
                Toast.makeText(requireContext(), it.data.name,Toast.LENGTH_LONG).show()

            }


        }
        pokemonAdapter.setOnItemClickListener {currentPokemon ->
            viewModel.getPokemonById(currentPokemon + 1)
            val bundle = Bundle().apply {
                putInt("id",currentPokemon)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_infoFragment,
                bundle
            )
        }

    }

    private fun hideProgressBar () {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager

            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            val visibleItemCount = layoutManager.childCount

            val totalItemCount = layoutManager.itemCount


            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage

            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount


            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10


            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                viewModel.getAllPokemons()
                isScrolling = false;
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    private fun initAdapter() {
        pokemonAdapter = PokemonAdapter(requireContext())

        rvPokemonItems.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(context,2)
            addOnScrollListener(scrollListener)

        }

    }
}