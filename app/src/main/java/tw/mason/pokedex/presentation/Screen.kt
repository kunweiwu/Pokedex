package tw.mason.pokedex.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


sealed class Screen(
    val route: String
) {

    object PokemonList : Screen(
        route = "pokemon_list_screen"
    )

    object PokemonDetail : Screen(
        route = "pokemon_detail_screen/{${NavArgsDef.DOMINANT_COLOR}}&{${NavArgsDef.POKEMON_NAME}}"
    ) {
        fun getNavRoute(
            dominantColor: Color,
            pokemonName: String
        ): String = "pokemon_detail_screen/${dominantColor.toArgb()}&$pokemonName"
    }
}