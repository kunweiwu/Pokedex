package tw.mason.pokedex.data.repository

import android.util.Log
import tw.mason.pokedex.common.Results
import tw.mason.pokedex.data.remote.PokeApi
import tw.mason.pokedex.data.remote.dto.PokemonListDto
import tw.mason.pokedex.data.remote.dto.toPokemonInfo
import tw.mason.pokedex.domain.model.PokemonInfo
import tw.mason.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val api: PokeApi) : PokemonRepository {

    override suspend fun getPokemon(limit: Int, offset: Int): Results<PokemonListDto> {
        val result = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Results.Error(e.localizedMessage ?: e.toString())
        }
        return Results.Success(result)
    }

    override suspend fun getPokemonInfo(name: String): Results<PokemonInfo> {
        val pokemon = try {
            api.getPokemonInfo(name).toPokemonInfo()
        } catch (e: Exception) {
            return Results.Error(e.localizedMessage ?: e.toString())
        }
        Log.i("PokemonRepositoryImpl", "getPokemonInfo: $pokemon")
        return Results.Success(pokemon)
    }

}