package apps.nb.working.pocmvvm.domain.usecases

import androidx.paging.PagingData
import apps.nb.working.pocmvvm.domain.SearchRepository
import apps.nb.working.pocmvvm.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    val totalResults: StateFlow<Int> = searchRepository.totalResults
    suspend operator fun invoke(queryTitle: String): Flow<PagingData<Movie>> {
        return searchRepository.getMoviesResult(queryTitle)
    }
}
