package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.usecases.GetMovieDetailsUseCase
import apps.nb.working.pocmvvm.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailMainViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    // StateFlow to display the movie details
    private val _movieDetailsState = MutableStateFlow<Resource<Movie>>(Resource.Idle)
    val movieDetailsState: MutableStateFlow<Resource<Movie>> = _movieDetailsState

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailsState.update {
                val result = getMovieDetailsUseCase(movieId)
                result
            }
        }
    }
}
