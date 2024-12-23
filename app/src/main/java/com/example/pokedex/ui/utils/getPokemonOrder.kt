package com.example.pokedex.ui.utils

// it should return a 3 digit number, completing 1 to 001, 21 to 021, etc
fun getPokemonOrder(order: Int): String {
    return when {
        order < 10 -> "#000$order"
        order < 100 -> "#00$order"
        order < 1000 -> "#0$order"
        else -> "#$order"
    }
}