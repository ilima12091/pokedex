package com.example.pokedex.ui.utils

import androidx.compose.ui.graphics.Color

enum class PokemonColorsByType(val color: Color) {
    BUG(Color(0xFFAEDF78)),
    WATER(Color(0xFF43CCFF)),
    GRASS(Color(0xFF00CA91)),
    FIRE(Color(0xFFE95C4D)),
    NORMAL(Color(0xFFA5A5A5)),
    POISON(Color(0xFF611380)),
    ELECTRIC(Color(0xFFF9BE00)),
    GROUND(Color(0xFFBFAC21)),
    FAIRY(Color(0xFFF87EA7)),
    FIGHTING(Color(0xFFE81319)),
    PSYCHIC(Color(0xFF8A0532)),
    GHOST(Color(0xFF8E55A4)),
    ROCK(Color(0xFF776A3E)),
    ICE(Color(0xFF66D1E5)),
    FLYING(Color(0xFF5EB9B2)),
    DARK(Color(0xFF2D221C)),
    DRAGON(Color(0xFF29036A)),
    STEEL(Color(0xFF7B8E8A)),
    UNKNOWN(Color(0xFF454545)),
    SHADOW(Color(0xFF4F4F4F));
}

fun getColorFromType(type: String): Color {
    return when (type.lowercase()) {
        "bug" -> PokemonColorsByType.BUG.color
        "water" -> PokemonColorsByType.WATER.color
        "grass" -> PokemonColorsByType.GRASS.color
        "fire" -> PokemonColorsByType.FIRE.color
        "normal" -> PokemonColorsByType.NORMAL.color
        "poison" -> PokemonColorsByType.POISON.color
        "electric" -> PokemonColorsByType.ELECTRIC.color
        "ground" -> PokemonColorsByType.GROUND.color
        "fairy" -> PokemonColorsByType.FAIRY.color
        "fighting" -> PokemonColorsByType.FIGHTING.color
        "psychic" -> PokemonColorsByType.PSYCHIC.color
        "ghost" -> PokemonColorsByType.GHOST.color
        "rock" -> PokemonColorsByType.ROCK.color
        "ice" -> PokemonColorsByType.ICE.color
        "flying" -> PokemonColorsByType.FLYING.color
        "dark" -> PokemonColorsByType.DARK.color
        "dragon" -> PokemonColorsByType.DRAGON.color
        "steel" -> PokemonColorsByType.STEEL.color
        "unknown" -> PokemonColorsByType.UNKNOWN.color
        "shadow" -> PokemonColorsByType.SHADOW.color
        else -> Color.Gray
    }
}
