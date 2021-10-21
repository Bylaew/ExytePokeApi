package com.bylaew.exytepokeapi.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.model.PokemonInfo.PokemonInfo
import com.bylaew.exytepokeapi.repository.PokemonRepository
import com.bylaew.exytepokeapi.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class InfoViewModel : ViewModel() {

    val pokemonRepository: PokemonRepository = PokemonRepository()
    val pokemonDetails : MutableLiveData<Resource<PokemonById>> = MutableLiveData()
    val pokemonAbout : MutableLiveData <Resource<PokemonInfo>> = MutableLiveData()

    var pokemonByIdResult : PokemonById? = null
    var pokemonDescriptionResult : PokemonInfo? = null

    fun getPokemonById(id: Int ) = viewModelScope.launch{
        pokemonDetails.postValue(Resource.Loading())
        val response = pokemonRepository.getPokemonById(id)
        pokemonDetails.postValue(handlePokemonByIdResponse(response))
    }

    fun getDescriptionPokemon(id:Int) = viewModelScope.launch{
        pokemonAbout.postValue(Resource.Loading())
        val response = pokemonRepository.getDescriptionPokemon(id)
        pokemonAbout.postValue(handlePokemonDescriptionResponse(response))
    }

    private fun handlePokemonByIdResponse(response: Response<PokemonById>): Resource<PokemonById>? {
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(pokemonByIdResult ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePokemonDescriptionResponse(response: Response<PokemonInfo>): Resource<PokemonInfo>? {
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.Success(pokemonDescriptionResult ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}