package com.example.pokedex.data.models

data class User(
    val email: String = "",
    val name: String = "",
    val lastName: String = "",
    val profilePictureUrl: String = "",
    val favorites: List<FavoritePokemon> = emptyList()
)