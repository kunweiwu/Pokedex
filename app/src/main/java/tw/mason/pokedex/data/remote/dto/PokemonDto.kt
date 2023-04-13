package tw.mason.pokedex.data.remote.dto


import com.google.gson.annotations.SerializedName
import tw.mason.pokedex.domain.model.PokemonInfo
import tw.mason.pokedex.domain.model.PokemonStats
import tw.mason.pokedex.domain.model.PokemonType

data class PokemonDto(
    val abilities: List<Ability>,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val forms: List<Form>,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndice>,
    val height: Int,
    @SerializedName("held_items")
    val heldItems: List<Any>,
    val id: Int,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    @SerializedName("past_types")
    val pastTypes: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

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
            PokemonStats(
                name = it.stat.name,
                baseStat = it.baseStat
            )
        },
        wight = weight,
        height = height
    )
}