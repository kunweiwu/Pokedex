package tw.mason.pokedex.presentation.pokemon_detail

import androidx.lifecycle.ViewModel
import tw.mason.pokedex.common.Results
import tw.mason.pokedex.domain.model.PokemonInfo
import tw.mason.pokedex.domain.repository.PokemonRepository

class PokemonDetailViewModel constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Results<PokemonInfo> {
        return repository.getPokemonInfo(pokemonName)
    }
}