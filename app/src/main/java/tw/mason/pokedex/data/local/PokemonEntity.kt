package tw.mason.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import tw.mason.pokedex.domain.model.PokemonStat
import tw.mason.pokedex.domain.model.PokemonType

@Entity
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val types: List<PokemonType>? = null,
    val stats: List<PokemonStat>? = null,
    val weight: Int? = null,
    val height: Int? = null,
)