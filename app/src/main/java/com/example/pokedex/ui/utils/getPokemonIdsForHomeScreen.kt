package com.example.pokedex.ui.utils

fun getPokemonIdsForHomeScreen(
    favoriteIds: List<String> = emptyList(),
    totalToShow: Int = Constants.DEFAULT_POKEMON_LIMIT_FOR_HOME_PAGE
): List<String> {
    val allIds= (1..Constants.Pokemon.LAST_POKEMON_ORDER).toList()
    val nonFavoriteOrders = allIds.filterNot { id -> id in favoriteIds.map{ it.toInt() } }

    return (favoriteIds
        .distinct()
        .take(totalToShow) +
            nonFavoriteOrders
                .take(totalToShow -favoriteIds .size)).map { it.toString() }
}