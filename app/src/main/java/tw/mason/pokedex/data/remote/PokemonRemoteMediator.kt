package tw.mason.pokedex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.gson.JsonParseException
import io.ktor.client.plugins.*
import kotlinx.coroutines.delay
import tw.mason.pokedex.data.local.PokemonDatabase
import tw.mason.pokedex.data.local.PokemonEntity
import tw.mason.pokedex.data.mappers.toPokemonEntity
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val db: PokemonDatabase,
    private val api: PokeApi
): RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 0 else lastItem.id / state.config.pageSize
                }
            }

            val pageCount = state.config.pageSize
            val pokemons = api.getPokemonList(
                offset = loadKey * pageCount,
                limit = pageCount
            )
            delay(2000)
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.clearAll()
                }
                val pokemonEntities = pokemons.results.map { it.toPokemonEntity() }
                db.dao.upsertAll(pokemonEntities)

                MediatorResult.Success(
                    endOfPaginationReached = pokemonEntities.isEmpty()
                )
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: ResponseException) {
            MediatorResult.Error(e)
        } catch (e: JsonParseException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }
}