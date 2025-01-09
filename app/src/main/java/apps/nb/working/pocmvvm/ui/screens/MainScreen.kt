package apps.nb.working.pocmvvm.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import apps.nb.working.pocmvvm.R
import apps.nb.working.pocmvvm.model.MainMoviesListState
import apps.nb.working.pocmvvm.ui.components.MovieListCarousel

@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val mainMoviesState = viewModel.mainMoviesState.collectAsStateWithLifecycle()

    Scaffold(topBar = { }, bottomBar = { }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            when (mainMoviesState.value) {
                is MainMoviesListState.Success -> {
                    MainContent(
                        mainMovies = mainMoviesState.value as MainMoviesListState.Success,
                        onMovieClick = onMovieClick
                    )
                }
                is MainMoviesListState.Error -> {
                    ErrorScreen(
                        "Error loading movies"
                    )
                }
                is MainMoviesListState.Loading -> {
                    LoadingScreen()
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun MainContent(
    mainMovies: MainMoviesListState.Success,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        MovieListCarousel(
            title = R.string.films_decouvrir,
            moviesFlow = mainMovies.discoverMoviesList.collectAsLazyPagingItems(),
            onMovieClick = onMovieClick
        )
        MovieListCarousel(
            title = R.string.upcoming_movies,
            moviesFlow = mainMovies.upcomingMoviesList.collectAsLazyPagingItems(),
            onMovieClick = onMovieClick
        )
        MovieListCarousel(
            title = R.string.trending_movies,
            moviesFlow = mainMovies.trendingMoviesList.collectAsLazyPagingItems(),
            onMovieClick = onMovieClick
        )
    }
}
