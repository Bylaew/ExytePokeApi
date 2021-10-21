package com.bylaew.exytepokeapi.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.bylaew.exytepokeapi.database.PokemonDB
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.repository.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel(contexto: Context): ViewModel() {

    private val repository: PokemonRepository
    var pokAll: LiveData<List<PokemonById>>

    init {
        val pokDB = PokemonDB.getDatabase(contexto)!!.pokemonDAO()
        repository = PokemonRepository(pokDB)
        pokAll = repository.getAllLocPoks()!!
    }


    fun insert(word: PokemonById) = viewModelScope.launch {
        repository.insert(word)
    }

}

class FavouriteViewModelFactory(private val contexto: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(contexto) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}