package apps.nb.working.pocmvvm.domain

import androidx.paging.PagingData
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.model.MovieGenre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MovieRepository {
    val totalRecords: StateFlow<Int>
    suspend fun getDiscoverMovies(): Flow<PagingData<Movie>>
    suspend fun getTrendingMovies(): Flow<PagingData<Movie>>
    suspend fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieByTitle(title: String): Resource<List<Movie>>
    suspend fun getMovieById(movieId: Int): Resource<Movie>
    suspend fun getMovieGenres(): List<MovieGenre>
}
