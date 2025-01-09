package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.domain.MovieRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.getMovieGenres()
}
