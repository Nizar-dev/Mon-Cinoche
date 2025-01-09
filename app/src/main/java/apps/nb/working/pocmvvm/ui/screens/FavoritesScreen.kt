package apps.nb.working.pocmvvm.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import apps.nb.working.pocmvvm.R
import apps.nb.working.pocmvvm.model.FavoriteMoviesState
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.ui.components.MovieItem
import apps.nb.working.pocmvvm.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoritesScreen(
    viewModel: FavoriteMoviesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val favoriteMoviesState = viewModel.favoriteMoviesState.collectAsStateWithLifecycle().value
    Scaffold(modifier = Modifier.padding(Spacing.spacing8), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(R.string.favorites_movies),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        })
    }) { innerPadding ->
        when (favoriteMoviesState) {
            is FavoriteMoviesState.Idle -> {
                Log.d("FavoritesScreen", "Idle")
            }

            is FavoriteMoviesState.Error -> {
                ErrorScreen(
                    errorMessage = favoriteMoviesState.message
                )
            }
            is FavoriteMoviesState.Loading -> {
                LoadingScreen()
            }
            is FavoriteMoviesState.Success -> {
                val movies = favoriteMoviesState.favoriteMovies
                FavoritesContent(
                    movies = movies,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun FavoritesContent(movies: List<Movie>, onMovieClick: (Int) -> Unit, modifier: Modifier) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing16)
    ) {
        items(movies) { item ->
            MovieItem(item, onMovieClick, sizeOfImage = 700)
        }
    }
}
