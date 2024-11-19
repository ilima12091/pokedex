package com.example.pokedex.api.responses

import com.google.gson.annotations.SerializedName

data class FetchPokemonSpeciesResponse(
    @SerializedName("evolution_chain") val evolutionChain: EvolutionChain
)

data class EvolutionChain(
    val url: String
)