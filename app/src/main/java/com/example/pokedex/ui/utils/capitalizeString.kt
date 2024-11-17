package com.example.pokedex.ui.utils

fun capitalizeString(string: String): String {
    return string.replaceFirstChar { it.uppercase() }
}