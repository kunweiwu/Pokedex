package tw.mason.pokedex.data.remote

import tw.mason.pokedex.data.remote.dto.PokemonDto
import tw.mason.pokedex.data.remote.dto.PokemonListDto

interface PokeApi {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListDto

    suspend fun getPokemonInfo(name: String): PokemonDto
}

