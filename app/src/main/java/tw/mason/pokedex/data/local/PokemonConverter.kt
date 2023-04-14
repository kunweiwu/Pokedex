package tw.mason.pokedex.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.mason.pokedex.domain.model.PokemonStat
import tw.mason.pokedex.domain.model.PokemonType

class PokemonConverter {

    private val gson = Gson()

    private val typeTypeToken = object : TypeToken<List<PokemonType>>() {}

    @TypeConverter
    fun formType(value: List<PokemonType>?): String? {
        val type = typeTypeToken.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toType(value: String?): List<PokemonType>? {
        val type = typeTypeToken.type
        return gson.fromJson(value, type)
    }

    private val statTypeToken = object : TypeToken<List<PokemonStat>>() {}

    @TypeConverter
    fun formStat(value: List<PokemonStat>?): String? {
        val type = statTypeToken.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStat(value: String?): List<PokemonStat>? {
        val type = statTypeToken.type
        return gson.fromJson(value, type)
    }
}