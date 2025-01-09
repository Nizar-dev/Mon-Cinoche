package apps.nb.working.pocmvvm.domain.usecases

import androidx.paging.PagingData
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val moviesRemoteRepo: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesRemoteRepo.getTrendingMovies()
    }
}
