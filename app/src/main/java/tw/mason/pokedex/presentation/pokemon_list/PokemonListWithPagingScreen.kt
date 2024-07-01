package tw.mason.pokedex.presentation.pokemon_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
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
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = CenterHorizontally,
                ) {
                    items(count = pokemons.itemCount) { index ->
                        val pokemon = pokemons[index] ?: return@items
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
                                color = MaterialTheme.colorScheme.secondary
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