package com.bylaew.exytepokeapi.model.PokemonById

import androidx.annotation.NonNull
import androidx.room.*
import com.bylaew.exytepokeapi.utils.Converter

@Entity
data class PokemonById(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int = 0,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_types: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)