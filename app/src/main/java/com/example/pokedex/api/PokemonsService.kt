package com.example.pokedex.api

import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonsService {
    @GET("pokemon/{pokemonName}")
    suspend fun fetchPokemonDetails(@Path("pokemonName") pokemonName: String): FetchPokemonDetailsResponse
}