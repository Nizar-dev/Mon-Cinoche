package apps.nb.working.pocmvvm.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.nb.working.pocmvvm.domain.usecases.GetDiscoverMoviesUseCase
import apps.nb.working.pocmvvm.domain.usecases.GetTrendingMoviesUseCase
import apps.nb.working.pocmvvm.domain.usecases.GetUpcomingMoviesUseCase
import apps.nb.working.pocmvvm.model.MainMoviesListState
import apps.nb.working.pocmvvm.model.MoviesTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {
    // StateFlow to display the selected tab
    private val _selectedTab = MutableStateFlow(MoviesTab.HOME)
    val selectedTab: MutableStateFlow<MoviesTab> = _selectedTab

    // StateFlow to display three lists of movies
    private val _mainMoviesState = MutableStateFlow<MainMoviesListState>(MainMoviesListState.Idle)
    val mainMoviesState: MutableStateFlow<MainMoviesListState> = _mainMoviesState

    // Initialize the ViewModel
    init {
        getMainMovies()
    }

    // Method to update the selected tab
    fun onTabSelected(tab: MoviesTab) {
        _selectedTab.value = tab
    }

    // Method to get the main movies list
    private fun getMainMovies() {
        viewModelScope.launch {
            _mainMoviesState.value = MainMoviesListState.Loading
            val discoverMovies = getDiscoverMoviesUseCase()
            val upcomingMovies = getUpcomingMoviesUseCase()
            val trendingMovies = getTrendingMoviesUseCase()
            _mainMoviesState.value = MainMoviesListState.Success(
                discoverMoviesList = discoverMovies,
                upcomingMoviesList = upcomingMovies,
                trendingMoviesList = trendingMovies
            )
        }
    }
}
