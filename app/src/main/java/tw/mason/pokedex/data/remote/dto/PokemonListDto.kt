package tw.mason.pokedex.data.remote.dto


data class PokemonListDto(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)