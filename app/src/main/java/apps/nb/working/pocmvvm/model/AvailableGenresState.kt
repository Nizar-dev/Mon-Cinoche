package apps.nb.working.pocmvvm.model

sealed class AvailableGenresState {
    // object Loading : AvailableGenresState()
    data class Success(val availableGenres: List<MovieGenre> = emptyList()) : AvailableGenresState()

    // data class Error(val message: String) : AvailableGenresState()
    data object Idle : AvailableGenresState()
}
