package com.example.pokedex.ui.utils

fun getPokemonIdsForHomeScreen(
    favoriteIds: List<String> = emptyList(),
    totalToShow: Int = 20
): List<String> {
    val allIds= (1..1000).toList()
    val nonFavoriteOrders = allIds.filterNot { id -> id in favoriteIds.map{ it.toInt() } }

    return (favoriteIds
        .distinct()
        .take(totalToShow) +
            nonFavoriteOrders
                .take(totalToShow -favoriteIds .size)).map { it.toString() }
}