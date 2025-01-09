package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.nb.working.pocmvvm.data.toFavoriteGenre
import apps.nb.working.pocmvvm.data.toMovieGenre
import apps.nb.working.pocmvvm.domain.usecases.GetFavoriteGenresUseCase
import apps.nb.working.pocmvvm.domain.usecases.GetMovieGenresUseCase
import apps.nb.working.pocmvvm.domain.usecases.UpdateFavoriteGenresUseCase
import apps.nb.working.pocmvvm.model.AvailableGenresState
import apps.nb.working.pocmvvm.model.FavoriteGenresState
import apps.nb.working.pocmvvm.model.MovieGenre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PreferencesViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getFavoriteMovieGenresUseCase: GetFavoriteGenresUseCase,
    private val updateFavoriteGenresUseCase: UpdateFavoriteGenresUseCase
) : ViewModel() {
    private val _selectedChoices = MutableStateFlow<List<MovieGenre>>(emptyList())
    val selectedChoices: MutableStateFlow<List<MovieGenre>> = _selectedChoices

    private val _availableChoices = MutableStateFlow<List<MovieGenre>>(emptyList())
    val availableChoices: MutableStateFlow<List<MovieGenre>> = _availableChoices

    private val _availableGenresState =
        MutableStateFlow<AvailableGenresState>(AvailableGenresState.Idle)
    val availableGenresState: MutableStateFlow<AvailableGenresState> = _availableGenresState

    private val _favoriteGenresState =
        MutableStateFlow<FavoriteGenresState>(FavoriteGenresState.Idle)
    val favoriteGenresState: MutableStateFlow<FavoriteGenresState> = _favoriteGenresState


    // Initialize the ViewModel
    init {
        getAvailableGenres()
        getFavoriteGenres()
        _availableChoices.update {
            availableChoices.value.filter { it !in selectedChoices.value }.sortedBy { it.name }
        }
    }

    private fun getAvailableGenres() {
        viewModelScope.launch {
            getFavoriteGenres()
            var result = getMovieGenresUseCase()
            result = result.filter { it !in selectedChoices.value }
            _availableGenresState.update {
                AvailableGenresState.Success(result)
            }
            _availableChoices.value =
                (availableGenresState.value as AvailableGenresState.Success).availableGenres


        }
    }

    /**
     * Get the favorite movie genres from the room database use case and update the state
     */
    private fun getFavoriteGenres() {
        viewModelScope.launch {
            val result = getFavoriteMovieGenresUseCase()
            _favoriteGenresState.update {
                FavoriteGenresState.Success(result.map { it.toMovieGenre() })
            }
            _selectedChoices.update {
                result.map { it.toMovieGenre() }.sortedBy { it.name }
            }
        }
    }

    // Method to update the selected choices
    internal fun addSelectedChoice(choice: MovieGenre) {
        if (!_selectedChoices.value.contains(choice)) {
            viewModelScope.launch {
                _selectedChoices.value = (selectedChoices.value + choice).sortedBy { it.name }
                updateFavoriteGenresUseCase(selectedChoices.value.map { it.toFavoriteGenre() })
            }
            _availableChoices.value = (availableChoices.value - choice).sortedBy { it.name }
        }
    }

    // Method to update the selected choices
    internal fun removeSelectedChoice(choice: MovieGenre) {
        viewModelScope.launch {
            _selectedChoices.value = (selectedChoices.value - choice).sortedBy { it.name }
            updateFavoriteGenresUseCase(selectedChoices.value.map { it.toFavoriteGenre() })

        }
        _availableChoices.value = (availableChoices.value + choice).sortedBy { it.name }
    }

    // Method to update the available choices
    internal fun addAvailableChoice(choice: MovieGenre) {
        if (!_availableChoices.value.contains(choice)) {
            _availableChoices.value = (availableChoices.value + choice).sortedBy { it.name }
            _selectedChoices.value = (selectedChoices.value - choice).sortedBy { it.name }
        }
    }
}
