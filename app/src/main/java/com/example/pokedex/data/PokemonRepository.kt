package com.example.pokedex.data

import com.example.pokedex.api.PokemonClient
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse

object PokemonRepository {
    suspend fun fetchPokemonDetails(name: String): FetchPokemonDetailsResponse {
        return PokemonClient.service.fetchPokemonDetails(name)
    }
}