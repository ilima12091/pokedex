package com.example.pokedex.ui.utils

fun getPokemonOrderFromUrl(url: String): Int {
    val urlParts = url.split("/")
    return urlParts[urlParts.size - 2].toInt()
}