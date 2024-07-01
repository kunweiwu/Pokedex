package tw.mason.pokedex.presentation.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import tw.mason.pokedex.data.local.PokemonEntity
import tw.mason.pokedex.data.mappers.toPokemon

class PokeListViewModel(
    pager: Pager<Int, PokemonEntity>
): ViewModel() {

    val pokemonPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPokemon() }
        }
        .cachedIn(viewModelScope)
}