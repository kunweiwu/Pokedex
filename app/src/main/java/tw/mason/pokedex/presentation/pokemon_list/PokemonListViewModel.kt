package tw.mason.pokedex.presentation.pokemon_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.mason.pokedex.common.Constants.PAGE_SIZE
import tw.mason.pokedex.common.Results
import tw.mason.pokedex.data.model.PokedexListEntry
import tw.mason.pokedex.domain.repository.PokemonRepository

class PokemonListViewModel constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            delay(1000)
            val offset = currentPage * PAGE_SIZE
            val result = repository.getPokemon(PAGE_SIZE, offset)
            when (result) {
                is Results.Success -> {
                    endReached.value = result.data.results.isEmpty()
                    val pokedexEntries = result.data.results.map { entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }.toInt()
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
                        PokedexListEntry(entry.name, url, number)
                    }
                    currentPage++

                    loadError.value = ""
                    pokemonList.value += pokedexEntries
                }
                is Results.Error -> {
                    loadError.value = result.message
                }
                else -> {}
            }
            isLoading.value = false
        }
    }

    init {
        loadPokemonPaginated()
    }
}