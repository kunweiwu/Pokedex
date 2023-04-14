package tw.mason.pokedex.domain.model

data class PokemonInfo(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val weight: Int,
    val height: Int,
)