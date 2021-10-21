package com.bylaew.exytepokeapi.model.Pokemon

import java.io.Serializable

data class PokemonResult (
    val name: String,
    val url: String
) : Serializable