package apps.nb.working.pocmvvm.domain

import androidx.paging.PagingData
import apps.nb.working.pocmvvm.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SearchRepository {
    val totalResults: MutableStateFlow<Int>
    suspend fun getMoviesResult(titleQuery: String): Flow<PagingData<Movie>>
}
