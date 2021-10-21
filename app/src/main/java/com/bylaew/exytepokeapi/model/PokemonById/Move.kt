package com.bylaew.exytepokeapi.model.PokemonById

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)