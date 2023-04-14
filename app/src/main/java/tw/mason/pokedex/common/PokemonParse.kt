package tw.mason.pokedex.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import tw.mason.pokedex.R
import tw.mason.pokedex.domain.model.PokemonStat
import tw.mason.pokedex.domain.model.PokemonType
import tw.mason.pokedex.presentation.ui.theme.*
import java.util.*


fun parseTypeToColor(type: PokemonType): Color {
    return when(type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: PokemonStat): Color {
    return when(stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: PokemonStat): String {
    return when(stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "攻擊"
        "defense" -> "防禦"
        "special-attack" -> "特攻"
        "special-defense" -> "特防"
        "speed" -> "速度"
        else -> ""
    }
}

// TODO("多國語系")
@Composable
fun getTypeString(type: PokemonType): String {
    return when(type.name.lowercase(Locale.ROOT)) {
        "normal" -> stringResource(id = R.string.normal)
        "fire" -> stringResource(id = R.string.fire)
        "water" -> "水"
        "electric" -> "電"
        "grass" -> "草"
        "ice" -> "冰"
        "fighting" -> "格鬥"
        "poison" -> "毒"
        "ground" -> "地面"
        "flying" -> "飛行"
        "psychic" -> "超能力"
        "bug" -> "蟲"
        "rock" -> "岩石"
        "ghost" -> "幽靈"
        "dragon" -> "龍"
        "dark" -> "惡"
        "steel" -> "鋼"
        "fairy" -> "妖精"
        else -> "???"
    }
}