package tw.mason.pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.mason.pokedex.common.Results
import tw.mason.pokedex.domain.model.PokemonInfo
import tw.mason.pokedex.presentation.pokemon_detail.PokemonDetailScreen
import tw.mason.pokedex.presentation.pokemon_detail.PokemonDetailViewModel
import tw.mason.pokedex.presentation.pokemon_list.PokeListViewModel
import tw.mason.pokedex.presentation.pokemon_list.PokemonListScreen
import tw.mason.pokedex.presentation.pokemon_list.PokemonListWithPagingScreen
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
                    startDestination = Screen.PokemonList.route,
                ) {
                    composable(route = Screen.PokemonList.route) {
                        val usePage = false
                        if (usePage) {
                            val viewModel = koinViewModel<PokeListViewModel>()
                            val pokemons = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()
                            PokemonListWithPagingScreen(
                                pokemons = pokemons,
                                navigateToDetail = { dominantColor, pokemonName ->
                                    navController.navigate(
                                        route = Screen.PokemonDetail.getNavRoute(
                                            dominantColor = dominantColor,
                                            pokemonName = pokemonName
                                        )
                                    )
                                }
                            )
                        } else {
                            PokemonListScreen(
                                navigateToDetail = { dominantColor, pokemonName ->
                                    navController.navigate(
                                        route = Screen.PokemonDetail.getNavRoute(
                                            dominantColor = dominantColor,
                                            pokemonName = pokemonName
                                        )
                                    )
                                }
                            )
                        }
                    }
                    composable(
                        route = Screen.PokemonDetail.route,
                        arguments = listOf(
                            navArgument(NavArgsDef.DOMINANT_COLOR) {
                                type = NavType.IntType
                            },
                            navArgument(NavArgsDef.POKEMON_NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            it.arguments?.getInt(NavArgsDef.DOMINANT_COLOR)?.let {
                                Color(it)
                            } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString(NavArgsDef.POKEMON_NAME) ?: ""
                        }
                        val viewModel: PokemonDetailViewModel by viewModel()
                        val pokemonInfo =
                            produceState<Results<PokemonInfo>>(initialValue = Results.Loading()) {
                                value = viewModel.getPokemonInfo(pokemonName)
                            }
                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            res = pokemonInfo.value,
                            popUp = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}