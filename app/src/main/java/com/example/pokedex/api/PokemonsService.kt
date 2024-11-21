package com.example.pokedex.api

import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.api.responses.FetchPokemonEvolutionResponse
import com.example.pokedex.api.responses.FetchPokemonListResponse
import com.example.pokedex.api.responses.FetchPokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonsService {

    @GET("pokemon")
    suspend fun fetchPokemons(@Query("offset") offset: Int, ): FetchPokemonListResponse

    @GET("pokemon/{pokemonName}")
    suspend fun fetchPokemonDetails(@Path("pokemonName") pokemonName: String): FetchPokemonDetailsResponse

    @GET("pokemon-species/{pokemonName}")
    suspend fun fetchPokemonSpecies(@Path("pokemonName") pokemonName: String): FetchPokemonSpeciesResponse

    @GET("evolution-chain/{evolutionId}")
    suspend fun fetchPokemonEvolutionChain(@Path("evolutionId") evolutionId: String): FetchPokemonEvolutionResponse
}