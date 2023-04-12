package tw.mason.pokedex.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.mason.pokedex.data.remote.KtorPokeApi
import tw.mason.pokedex.data.repository.PokemonRepositoryImpl
import tw.mason.pokedex.domain.repository.PokemonRepository
import tw.mason.pokedex.presentation.pokemon_detail.PokemonDetailViewModel
import tw.mason.pokedex.presentation.pokemon_list.PokemonListViewModel

val appModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(KtorPokeApi) }
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
}