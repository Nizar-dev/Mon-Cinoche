package apps.nb.working.pocmvvm.model

import apps.nb.working.pocmvvm.ui.moviestate.MovieUiState

sealed class MovieResult {
    data class Success(val data: MovieUiState) : MovieResult()
    data class Error(val exception: Throwable) : MovieResult()
    data object Loading : MovieResult()
}
