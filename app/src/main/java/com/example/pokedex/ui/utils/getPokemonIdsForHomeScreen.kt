package com.example.pokedex.ui.utils

fun getPokemonIdsForHomeScreen(
    favoriteOrders: List<String> = emptyList(),
    totalToShow: Int = 20
): List<String> {
    val allOrders = (1..1000).toList()
    val nonFavoriteOrders = allOrders.filterNot { it in favoriteOrders.map{ it.toInt() } }

    return (favoriteOrders
        .distinct()
        .take(totalToShow) +
            nonFavoriteOrders
                .take(totalToShow - favoriteOrders.size)).map { it.toString() }
}