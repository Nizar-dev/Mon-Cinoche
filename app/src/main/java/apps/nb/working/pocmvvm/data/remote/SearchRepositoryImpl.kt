package apps.nb.working.pocmvvm.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import apps.nb.working.pocmvvm.domain.SearchRepository
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.network.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : SearchRepository {
    private val _totalResults = MutableStateFlow(0)
    override val totalResults: MutableStateFlow<Int> = _totalResults
    override suspend fun getMoviesResult(titleQuery: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    val response = movieApi.getMoviesByTitle(query = titleQuery, page = page)
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        _totalResults.update { moviesResponse?.totalResults ?: 0 }
                        convertApiResponseToMovies(moviesResponse!!)
                    } else {
                        throw Exception("Error loading movies: ${response.message()}")
                    }
                }
            }
        ).flow
    }
}
