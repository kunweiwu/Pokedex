package tw.mason.pokedex.common

sealed class UiState<T> {
    object Loading : UiState<Nothing>()
    class Success<T>(val data: T) : UiState<T>()
    class Error(val message: String) : UiState<Nothing>()
}