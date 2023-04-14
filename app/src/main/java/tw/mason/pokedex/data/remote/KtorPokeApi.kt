package tw.mason.pokedex.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import tw.mason.pokedex.common.Constants
import tw.mason.pokedex.data.remote.dto.PokemonDto
import tw.mason.pokedex.data.remote.dto.PokemonListDto

object KtorPokeApi: PokeApi {

    private val client = HttpClient(OkHttp) {
        expectSuccess = true
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = Constants.API_HOST
            }
        }
        install(ContentNegotiation) { gson() }
    }

    private const val API_PREFIX = "/api/v2"

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListDto {
        return client.get("$API_PREFIX/pokemon") {
            url {
                parameters.append("limit", limit.toString())
                parameters.append("offset", offset.toString())
            }
        }.body()
    }

    override suspend fun getPokemonInfo(name: String): PokemonDto {
        return client.get("$API_PREFIX/pokemon/$name").body()
    }
}