package apps.nb.working.pocmvvm.model

sealed class FavoriteMoviesState {
    data object Loading : FavoriteMoviesState()
    data class Success(val favoriteMovies: List<Movie> = emptyList()) : FavoriteMoviesState()
    data class Error(val message: String) : FavoriteMoviesState()
    data object Idle : FavoriteMoviesState()
}
