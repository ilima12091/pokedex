package com.example.pokedex.api.mappers

import com.example.pokedex.api.responses.PokemonType

fun List<PokemonType>.toTypeNames(): List<String> {
    return this.map { it.type.name }
}