package tw.mason.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [PokemonConverter::class]
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val dao: PokemonDao
}