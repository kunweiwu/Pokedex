package tw.mason.pokedex.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.mason.pokedex.common.Constants
import tw.mason.pokedex.data.local.PokemonDatabase
import tw.mason.pokedex.data.remote.KtorPokeApi
import tw.mason.pokedex.data.remote.PokemonRemoteMediator
import tw.mason.pokedex.data.repository.PokemonRepositoryImpl
import tw.mason.pokedex.domain.repository.PokemonRepository
import tw.mason.pokedex.presentation.pokemon_detail.PokemonDetailViewModel
import tw.mason.pokedex.presentation.pokemon_list.PokeListViewModel
import tw.mason.pokedex.presentation.pokemon_list.PokemonListViewModel

val appModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(KtorPokeApi) }
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = PokemonDatabase::class.java,
            name = "pokemon.db"
        ).build()
    }
    @OptIn(ExperimentalPagingApi::class)
    single {
        val db = get<PokemonDatabase>()
        Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            remoteMediator = PokemonRemoteMediator(
                db = db,
                api = KtorPokeApi
            ),
            pagingSourceFactory = {
                db.dao.paginSource()
            }
        )
    }
    viewModel { PokeListViewModel(get()) }
}