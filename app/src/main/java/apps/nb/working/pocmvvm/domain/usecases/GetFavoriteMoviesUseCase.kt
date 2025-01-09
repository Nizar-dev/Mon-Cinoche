package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.model.Movie
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        val favoriteItems = favoriteRepository.getAllFavorites()
        val favoriteMovies = mutableListOf<Movie>()
        for (item in favoriteItems) {
            val result = movieRepository.getMovieById(item.movieId)
            if (result is Resource.Success) {
                result.data.let { favoriteMovies.add(it) }
            }
        }
        return favoriteMovies
    }
}
