package com.example.pokedex.data.models

data class User(
    val email: String,
    val name: String? = null,
    val lastName: String? = null,
    val profilePictureUrl: String? = null,
    val favorites: List<FavoritePokemon> = emptyList()
)