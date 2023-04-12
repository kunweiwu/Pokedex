package tw.mason.pokedex.domain.repository

import tw.mason.pokedex.common.Results
import tw.mason.pokedex.domain.model.Pokemon
import tw.mason.pokedex.domain.model.PokemonList


interface PokemonRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Results<PokemonList>

    suspend fun getPokemonInfo(name: String): Results<Pokemon>
}

