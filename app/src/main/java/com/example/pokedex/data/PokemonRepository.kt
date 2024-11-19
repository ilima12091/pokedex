package com.example.pokedex.data

import com.example.pokedex.api.PokemonClient
import com.example.pokedex.api.responses.Chain
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.api.responses.Species

object PokemonRepository {
    suspend fun fetchPokemonDetails(name: String): FetchPokemonDetailsResponse {
        return PokemonClient.service.fetchPokemonDetails(name)
    }

    suspend fun fetchPokemonEvolutionChain(name: String): List<Species> {
        val speciesResponse = PokemonClient.service.fetchPokemonSpecies(name)

        val evolutionChainUrl = speciesResponse.evolutionChain.url
        val splittedChain = evolutionChainUrl.split("/")
        val evolutionId = splittedChain[splittedChain.size - 2]
        val evolutionResponse = PokemonClient.service.fetchPokemonEvolutionChain(evolutionId)

        val result = mutableListOf<Species>()

        var currentChain: Chain? = evolutionResponse.chain

        while (currentChain != null) {
            val species = currentChain.species
            val pokemonDetails = fetchPokemonDetails(species.name)
            species.pokemonDetails = pokemonDetails

            result.add(species)
            currentChain = currentChain.evolvesTo.getOrNull(0)
        }

        return result
    }
}