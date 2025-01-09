package apps.nb.working.pocmvvm.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.model.MovieGenre
import apps.nb.working.pocmvvm.network.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    private val _totalResults = MutableStateFlow(0)
    override val totalRecords: MutableStateFlow<Int> = _totalResults
    override suspend fun getDiscoverMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    val response = movieApi.getDiscoverMovies(page = page)
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (page == STARTING_PAGE_INDEX) {
                            _totalResults.value = moviesResponse?.totalResults ?: 0
                        }
                        convertApiResponseToMovies(moviesResponse!!)
                    } else {
                        throw Exception("Error loading movies: ${response.message()}")
                    }
                }
            }

        ).flow
    }
    override suspend fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    val response = movieApi.getTrendingMovies(page = page)
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (page == STARTING_PAGE_INDEX) {
                            _totalResults.value = moviesResponse?.totalResults ?: 0
                        }
                        convertApiResponseToMovies(moviesResponse!!)
                    } else {
                        throw Exception("Error loading movies: ${response.message()}")
                    }
                }
            }

        ).flow
    }
    override suspend fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSource { page ->
                    val response = movieApi.getUpcomingMovies(page = page)
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (page == STARTING_PAGE_INDEX) {
                            _totalResults.value = moviesResponse?.totalResults ?: 0
                        }
                        convertApiResponseToMovies(moviesResponse!!)
                    } else {
                        throw Exception("Error loading movies: ${response.message()}")
                    }
                }
            }

        ).flow
    }

    override suspend fun getMovieByTitle(title: String): Resource<List<Movie>> {
        return try {
            val response: Response<MovieApiResponse> = movieApi.getMoviesByTitle(query = title)
            if (response.isSuccessful) {
                val movies = convertApiResponseToMovies(response.body()!!)
                Resource.Success(
                    data = movies,
                    totalRecords = response.body()!!.totalResults
                )
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getMovieById(movieId: Int): Resource<Movie> {
        return try {
            val response: Response<MovieResult> = movieApi.getMovieDetails(movieId)
            if (response.isSuccessful) {
                val movie = convertApiResponseToMovie(response.body()!!)
                Resource.Success(movie)
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getMovieGenres(): List<MovieGenre> {
        val response: Response<GenreApiResponse> = movieApi.getMovieGenres()
        return if (response.isSuccessful) {
            convertApiResponseGenreToGenres(response.body()!!)
        } else {
            emptyList()
        }
    }
}
