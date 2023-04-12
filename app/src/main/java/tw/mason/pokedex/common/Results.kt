package tw.mason.pokedex.common

sealed class Results<T> {
    class Success<T>(val data: T) : Results<T>()
    class Error<T>(val message: String) : Results<T>()
    class Loading<T>: Results<T>()
}