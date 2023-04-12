package tw.mason.pokedex.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavController

object PokedexNav {
    object List {
        const val SCREEN = "pokemon_list_screen"
    }

    object Detail {
        const val PREFIX = "pokemon_detail_screen"
        const val ARG_DOMINANT_COLOR = "dominantColor"
        const val ARG_POKEMON_NAME = "pokemonName"
        const val SCREEN = "$PREFIX/{$ARG_DOMINANT_COLOR}/{$ARG_POKEMON_NAME}"
    }
}

fun NavController.navigateToDetail(
    dominantColor: Color,
    pokemonName: String
) {
    navigate("${PokedexNav.Detail.PREFIX}/${dominantColor.toArgb()}/$pokemonName")
}