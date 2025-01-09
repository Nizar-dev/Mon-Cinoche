package apps.nb.working.pocmvvm.model

sealed class FavoriteGenresState {
    // object Loading : FavoriteGenresState()
    data class Success(val genres: List<MovieGenre> = emptyList()) : FavoriteGenresState()

    // data class Error(val message: String) : FavoriteGenresState()
    data object Idle : FavoriteGenresState()
}
