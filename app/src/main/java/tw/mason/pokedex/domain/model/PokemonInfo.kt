package tw.mason.pokedex.domain.model

data class PokemonInfo(
    val name: String,
    val id: Int,
    // Pokemon from the front in battle.
    val imageUrl: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStats>,
    val wight: Int,
    val height: Int,
)
