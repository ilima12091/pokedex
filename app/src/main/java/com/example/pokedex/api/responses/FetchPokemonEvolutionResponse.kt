package com.example.pokedex.api.responses

import com.google.gson.annotations.SerializedName

data class FetchPokemonEvolutionResponse(
    val chain: Chain,
)

data class Chain(
    @SerializedName("evolves_to") val evolvesTo: List<Chain>,
    val species: Species
)

data class Species(
    val name: String,
    var pokemonDetails: FetchPokemonDetailsResponse
)