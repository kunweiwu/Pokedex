package tw.mason.pokedex.presentation.pokemon_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import tw.mason.pokedex.R
import tw.mason.pokedex.data.model.PokedexListEntry
import tw.mason.pokedex.domain.model.Pokemon

@Composable
fun PokemonListWithPagingScreen(
    pokemons: LazyPagingItems<Pokemon>,
    navigateToDetail: (dominantColor: Color, pokemonName: String) -> Unit,
) {
    var errorMessage by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = pokemons.loadState) {
        if (pokemons.loadState.refresh is LoadState.Error) {
            val message =
                (pokemons.loadState.refresh as LoadState.Error).error.message ?: "發生未預期的錯誤"
            errorMessage = message
        }
    }
    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (pokemons.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(CenterHorizontally),
                    color = MaterialTheme.colors.primary
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = CenterHorizontally,
                ) {
                    items(pokemons) { pokemon ->
                        if (pokemon == null) return@items
                        val entry1 = PokedexListEntry(
                            pokemon.name,
                            pokemon.getImageUrlById(),
                            pokemon.id
                        )
                        PokedexItem(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            entry = entry1,
                            navigateToDetail = navigateToDetail,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    item {
                        if (pokemons.loadState.append is LoadState.Loading)
                            CircularProgressIndicator(
                                modifier = Modifier.align(CenterHorizontally),
                                color = MaterialTheme.colors.secondary
                            )
                    }
                }
            }
        }
    }

    if (errorMessage.isNotEmpty()) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

fun Pokemon.getImageUrlById(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}