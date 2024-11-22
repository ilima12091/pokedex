package com.example.pokedex.data.models

data class FavoritePokemon(
    val id: String = "",
    val name: String = "",
    val sprite: String = "",
    val types: List<String> = emptyList(),
)