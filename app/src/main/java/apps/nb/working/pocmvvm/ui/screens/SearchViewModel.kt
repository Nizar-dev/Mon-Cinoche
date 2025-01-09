package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.usecases.SearchUseCase
import apps.nb.working.pocmvvm.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _searchMovieState =
        MutableStateFlow<Resource<Flow<PagingData<Movie>>>>(Resource.Idle)
    val searchMovieState: MutableStateFlow<Resource<Flow<PagingData<Movie>>>> = _searchMovieState
    fun searchMovies(titleQuery: String) {
        _searchMovieState.value = Resource.Loading
        viewModelScope.launch {
            val pagingDataFlow = searchUseCase(titleQuery)
            searchUseCase.totalResults.collect { total ->
                _searchMovieState.value = Resource.Success(
                    data = pagingDataFlow,
                    totalRecords = total
                )
            }
        }
    }
}
