package tw.mason.pokedex.data.mappers

import tw.mason.pokedex.data.local.PokemonEntity
import tw.mason.pokedex.data.remote.dto.PokemonDto
import tw.mason.pokedex.data.remote.dto.PokemonListDto
import tw.mason.pokedex.domain.model.Pokemon
import tw.mason.pokedex.domain.model.PokemonInfo
import tw.mason.pokedex.domain.model.PokemonStat
import tw.mason.pokedex.domain.model.PokemonType

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        id = id,
        imageUrl = sprites.frontDefault,
        types = types.map {
            PokemonType(
                name = it.type.name
            )
        },
        stats = stats.map {
            PokemonStat(
                name = it.stat.name,
                baseStat = it.baseStat
            )
        },
        weight = weight,
        height = height
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name,
        id = id
    )
}

fun PokemonDto.toPokemonInfo(): PokemonInfo {
    return PokemonInfo(
        name = name,
        id = id,
        imageUrl = sprites.frontDefault,
        types = types.map {
            PokemonType(
                name = it.type.name
            )
        },
        stats = stats.map {
            PokemonStat(
                name = it.stat.name,
                baseStat = it.baseStat
            )
        },
        weight = weight,
        height = height
    )
}

fun PokemonListDto.Result.toPokemonEntity(): PokemonEntity {
    val id = if (url.endsWith("/")) {
        url.dropLast(1).takeLastWhile { it.isDigit() }
    } else {
        url.takeLastWhile { it.isDigit() }
    }.toInt()
    return PokemonEntity(
        id = id,
        name = name
    )
}