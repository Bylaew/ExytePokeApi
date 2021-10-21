package com.bylaew.exytepokeapi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bylaew.exytepokeapi.model.*
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.model.PokemonInfo.Pokemon
import com.bylaew.exytepokeapi.utils.Converter

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM pokemonbyid WHERE id = :id")
    fun getById(id: String?): LiveData<PokemonById>

    @Query("SELECT * FROM pokemonbyid WHERE id IN(:evolutionIds)")
    fun getEvolutionsByIds(evolutionIds: List<String>): LiveData<List<PokemonById>>

    @Query("SELECT * FROM pokemonbyid")
    fun all(): LiveData<List<PokemonById>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(pokemon: List<PokemonById>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOne(pokemon: PokemonById)

    @Query("DELETE FROM pokemonbyid")
    fun deleteAll()

    @Delete
    fun delete(model: PokemonById)
}