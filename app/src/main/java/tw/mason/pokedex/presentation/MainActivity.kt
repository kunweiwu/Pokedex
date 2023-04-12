package tw.mason.pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tw.mason.pokedex.presentation.pokemon_list.PokemonListScreen
import tw.mason.pokedex.presentation.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // 這個 API 提供我們換頁的方法和有返回上頁的[currentBackStackEntryAsState()]
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = PokedexRouter.List.SCREEN,
                ) {
                    composable(route = PokedexRouter.List.SCREEN) {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        route = PokedexRouter.Detail.SCREEN,
                        arguments = listOf(
                            navArgument(PokedexRouter.Detail.ARG_DOMINANT_COLOR) {
                                type = NavType.IntType
                            },
                            navArgument(PokedexRouter.Detail.ARG_POKEMON_NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            it.arguments?.getInt(PokedexRouter.Detail.ARG_DOMINANT_COLOR)?.let {
                                Color(it)
                            } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString(PokedexRouter.Detail.ARG_POKEMON_NAME) ?: ""
                        }
                    }
                }
            }
        }
    }
}

object PokedexRouter {
    object List {
        const val SCREEN = "pokemon_list_screen"
    }

    object Detail {
        private const val prefix = "pokemon_detail_screen"
        const val ARG_DOMINANT_COLOR = "dominantColor"
        const val ARG_POKEMON_NAME = "pokemonName"
        const val SCREEN = "$prefix/{$ARG_DOMINANT_COLOR}/{$ARG_POKEMON_NAME}"

        fun buildRoute(dominantColor: Int, pokemonName: String): String {
            return "$prefix/{$dominantColor}/{$pokemonName}"
        }
    }
}