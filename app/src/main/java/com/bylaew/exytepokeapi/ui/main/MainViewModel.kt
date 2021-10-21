package com.bylaew.exytepokeapi.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bylaew.exytepokeapi.database.PokemonDB
import com.bylaew.exytepokeapi.model.Pokemon.PokemonResponse
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.repository.PokemonRepository
import com.bylaew.exytepokeapi.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel(){

    var pokemonRepository: PokemonRepository = PokemonRepository()
    val pokemonList : MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    var pokemonResponse : PokemonResponse? = null

    val pokemonDetails : MutableLiveData<Resource<PokemonById>> = MutableLiveData()

    var pokemonByIdResult : PokemonById? = null

    var pokemonPages = 0
    private var offset = 0
    private var limit = 10

    init {
        getAllPokemons()
    }

    suspend fun putLike(context: Context, pok: PokemonById) {
        pokemonRepository = PokemonRepository(PokemonDB.getDatabase(context)!!.pokemonDAO())
        pokemonRepository.insert(pok)
    }

    fun getPokemonById(id: Int ) = viewModelScope.launch{
        pokemonDetails.postValue(Resource.Loading())
        val response = pokemonRepository.getPokemonById(id)
        pokemonDetails.postValue(handlePokemonByIdResponse(response))
    }

    fun getAllPokemons() = viewModelScope.launch{
        pokemonList.postValue(Resource.Loading())
        val response = pokemonRepository.getAllPokemons(limit,offset)
        pokemonList.postValue(handlegetAllPokemonsResponse(response))
    }

    private fun handlePokemonByIdResponse(response: Response<PokemonById>): Resource<PokemonById>? {
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(pokemonByIdResult ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlegetAllPokemonsResponse(response: Response<PokemonResponse>) : Resource<PokemonResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                pokemonPages++
                offset = pokemonPages * 10
                limit = 10
                if(pokemonResponse == null){
                    pokemonResponse = resultResponse
                }else{
                    val oldPokemons = pokemonResponse?.results
                    val newPokemons = resultResponse.results
                    oldPokemons?.addAll(newPokemons)
                }
                return Resource.Success(pokemonResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}