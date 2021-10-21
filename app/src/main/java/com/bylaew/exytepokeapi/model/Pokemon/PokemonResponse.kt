package com.bylaew.exytepokeapi.model.Pokemon

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: MutableList<PokemonResult>
)