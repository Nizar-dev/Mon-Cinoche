package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.model.Movie
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<Movie> =
        movieRepository.getMovieById(movieId)
}
