package tw.mason.pokedex.data.repository

import tw.mason.pokedex.common.Results
import tw.mason.pokedex.data.remote.PokeApi
import tw.mason.pokedex.domain.model.Pokemon
import tw.mason.pokedex.domain.model.PokemonList
import tw.mason.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val api: PokeApi) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Results<PokemonList> {
        val result = api.getPokemonList(limit, offset)
        val list: List<Pokemon> = try {
            result.results.map { entry ->
                val id = if (entry.url.endsWith("/")) {
                    entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    entry.url.takeLastWhile { it.isDigit() }
                }.toInt()
                Pokemon(name = entry.name, id = id)
            }
        } catch (e: Exception) {
            return Results.Error(e.localizedMessage ?: e.toString())
        }
        return Results.Success(PokemonList(
            count = result.count,
            list = list
        ))
    }

    override suspend fun getPokemonInfo(name: String): Results<Pokemon> {
        val pokemon = try {
            api.getPokemonInfo(name).let {
                Pokemon(name = it.name, id = it.id)
            }
        } catch (e: Exception) {
            return Results.Error(e.localizedMessage ?: e.toString())
        }
        return Results.Success(pokemon)
    }

}