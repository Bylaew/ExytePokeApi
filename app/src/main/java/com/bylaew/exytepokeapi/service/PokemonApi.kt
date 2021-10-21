package com.bylaew.exytepokeapi.service

import com.bylaew.exytepokeapi.model.Pokemon.PokemonResponse
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.model.PokemonInfo.PokemonInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPi {

    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit")
        limit: Int = 10,
        @Query("offset")
        offset: Int = 0
    ): Response<PokemonResponse>

    @GET("https://pokeapi.co/api/v2/pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): Response<PokemonById>

    @GET("https://pokeapi.co/api/v2/pokemon-species/{id}")
    suspend fun getInfoPokemon(
        @Path("id") id: Int
    ) : Response<PokemonInfo>

}