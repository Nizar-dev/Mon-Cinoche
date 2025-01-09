package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.nb.working.pocmvvm.core.SnackBarMessage
import apps.nb.working.pocmvvm.domain.usecases.AddFavoriteUseCase
import apps.nb.working.pocmvvm.domain.usecases.GetFavoriteMoviesUseCase
import apps.nb.working.pocmvvm.domain.usecases.RemoveFavoriteUseCase
import apps.nb.working.pocmvvm.model.FavoriteMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteMoviesViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _favoriteMoviesState =
        MutableStateFlow<FavoriteMoviesState>(FavoriteMoviesState.Idle)
    val favoriteMoviesState: MutableStateFlow<FavoriteMoviesState> = _favoriteMoviesState

    // State to display a snack bar message when a movie is added or removed from favorites
    private val _snackBarMessage = MutableStateFlow<SnackBarMessage?>(null)
    val snackBarMessage: MutableStateFlow<SnackBarMessage?> = _snackBarMessage

    // Initialize the ViewModel
    init {
        getFavoriteMovies()
    }

    // Set the snack bar message
    private fun setSnackBar(message: SnackBarMessage) {
        _snackBarMessage.update {
            message
        }
    }

    // Clear the snack bar message
    fun clearSnackBar() {
        _snackBarMessage.value = null
    }

    /**
     * Add a movie to favorites and update the state
     */
    fun addToFavorites(movieId: Int) {
        viewModelScope.launch {
            addFavoriteUseCase(movieId)
            setSnackBar(SnackBarMessage.ADD_TO_FAVORITES)
            getFavoriteMovies()
        }
    }

    /**
     * Remove a movie from favorites and update the state
     */
    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            removeFavoriteUseCase(movieId)
            getFavoriteMovies()
            setSnackBar(SnackBarMessage.REMOVE_FROM_FAVORITES)
        }
    }

    /**
     * Get the favorite movies from room and update the state
     */
    private fun getFavoriteMovies() {
        viewModelScope.launch {
            val result = getFavoriteMoviesUseCase()
            _favoriteMoviesState.update {
                FavoriteMoviesState.Success(result)
            }
        }
    }
}
