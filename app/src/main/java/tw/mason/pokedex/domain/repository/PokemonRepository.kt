package tw.mason.pokedex.domain.repository

import tw.mason.pokedex.common.Results
import tw.mason.pokedex.data.remote.dto.PokemonListDto
import tw.mason.pokedex.domain.model.PokemonInfo


interface PokemonRepository {

    suspend fun getPokemon(limit: Int, offset: Int): Results<PokemonListDto>

    suspend fun getPokemonInfo(name: String): Results<PokemonInfo>
}

