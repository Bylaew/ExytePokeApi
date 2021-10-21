package com.bylaew.exytepokeapi.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bylaew.exytepokeapi.database.PokemonDAO
import com.bylaew.exytepokeapi.database.PokemonDB
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.service.ServiceInstance
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val pokDao: PokemonDAO? = null) {
    val allPoks: LiveData<List<PokemonById>>? = pokDao?.all()


    @WorkerThread
    suspend fun insert(pok: PokemonById) {
        pokDao?.addOne(pok)
    }

    fun getAllLocPoks() = pokDao?.all()

    suspend fun getAllPokemons(limit: Int,offset:Int) =
        ServiceInstance.api.getAllPokemons(limit,offset)

    suspend fun getPokemonById(id : Int) =
        ServiceInstance.api.getPokemonById(id)

    suspend fun getDescriptionPokemon(id: Int) =
        ServiceInstance.api.getInfoPokemon(id)
}