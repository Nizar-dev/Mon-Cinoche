package apps.nb.working.pocmvvm.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class MainMoviesListState {
    data class Success(
        val discoverMoviesList: Flow<PagingData<Movie>>,
        val trendingMoviesList: Flow<PagingData<Movie>>,
        val upcomingMoviesList: Flow<PagingData<Movie>>
    ) : MainMoviesListState()
    data class Error(val message: String) : MainMoviesListState()
    data object Idle : MainMoviesListState()
    data object Loading : MainMoviesListState()
}
