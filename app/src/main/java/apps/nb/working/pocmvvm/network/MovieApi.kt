package apps.nb.working.pocmvvm.network

import apps.nb.working.pocmvvm.data.remote.GenreApiResponse
import apps.nb.working.pocmvvm.data.remote.MovieApiResponse
import apps.nb.working.pocmvvm.data.remote.MovieResult
import apps.nb.working.pocmvvm.model.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    /**
     * getting popular movies
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<MovieApiResponse>

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = true
    ): Response<MovieApiResponse>

    /**
     * getting upcoming movies
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
         @Query("sort_by") sortBy: String = "date.asc",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "fr-FR"
    ): Response<MovieApiResponse>

    /**
     * getting trending movies
     */
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "fr-FR"
    ): Response<MovieApiResponse>

    /**
     * getting movie by title
     */
    @GET("search/movie")
    suspend fun getMoviesByTitle(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("query") query: String = "The Dark Knight",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "fr-FR",
        @Query("include_adult") includeAdult: Boolean = false
    ): Response<MovieApiResponse>

    /**
     * getting a movie details by id
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("language") language: String = "fr-FR"
    ): Response<MovieResult>

    /**
     * getting movie genres list
     */
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
        @Query("language") language: String = "fr-FR"
    ): Response<GenreApiResponse>
}
