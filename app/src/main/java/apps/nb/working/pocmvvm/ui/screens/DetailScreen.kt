package apps.nb.working.pocmvvm.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import apps.nb.working.pocmvvm.R
import apps.nb.working.pocmvvm.core.SnackBarMessage
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.model.FavoriteMoviesState
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.ui.components.shimmerBrush
import apps.nb.working.pocmvvm.ui.theme.Spacing
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
internal fun DetailScreen(
    navController: NavController,
    viewModel: DetailMainViewModel = hiltViewModel(),
    favoriteMoviesViewModel: FavoriteMoviesViewModel,
    movieId: Int
) {
    viewModel.getMovieDetails(movieId)
    val movieDetailsState = viewModel.movieDetailsState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarMessage = favoriteMoviesViewModel.snackBarMessage.collectAsStateWithLifecycle()
    val favoriteMoviesState = favoriteMoviesViewModel.favoriteMoviesState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val messageAddToFavorites = stringResource(R.string.movie_added_to_favorites)
    val messageRemoveFromFavorites = stringResource(R.string.movie_removed_from_favorites)
    LaunchedEffect(snackBarMessage.value) {
        snackBarMessage.value?.let { messageToShow ->
            scope.launch {
                when (messageToShow) {
                    SnackBarMessage.ADD_TO_FAVORITES -> snackBarHostState.showSnackbar(
                        messageAddToFavorites
                    )

                    SnackBarMessage.REMOVE_FROM_FAVORITES -> snackBarHostState.showSnackbar(
                        messageRemoveFromFavorites
                    )
                }
                favoriteMoviesViewModel.clearSnackBar()
            }
        }
    }
    Scaffold(
        modifier = Modifier.padding(Spacing.spacing8),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        when (movieDetailsState.value) {
            is Resource.Loading -> {
                LoadingScreen()
            }
            is Resource.Success -> {
                val movie = (movieDetailsState.value as Resource.Success).data
                val favoriteMovies =
                    (favoriteMoviesState.value as FavoriteMoviesState.Success).favoriteMovies
                val isFavorite = favoriteMovies.any { it.id == movie.id }
                MovieDetailContent(
                    paddingValues = paddingValues,
                    movie = movie,
                    onNavigateHome = { navController.popBackStack() },
                    isFavorite = isFavorite,
                    onFavoriteClick = {
                        if (!isFavorite) {
                            favoriteMoviesViewModel.addToFavorites(it)
                        } else {
                            favoriteMoviesViewModel.removeFromFavorites(it)
                        }
                    }
                )
            }
            is Resource.Error -> {
                val message = (movieDetailsState.value as Resource.Error).message
                ErrorScreen(
                    errorMessage = message
                )
            }
            is Resource.Idle -> {
                ErrorScreen(
                    stringResource(R.string.idle_state)
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailContent(
    paddingValues: PaddingValues,
    movie: Movie,
    onNavigateHome: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: (Int) -> Unit
) {
    val showShimmer = remember { mutableStateOf(true) }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = movie.title,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        })
    }, bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onNavigateHome,
                    modifier = Modifier.padding(Spacing.spacing16)
                ) {
                    Text(text = stringResource(R.string.revenir_accueil))
                }
                Button(
                    onClick = {
                        onFavoriteClick(movie.id)
                    },
                    modifier = Modifier.padding(Spacing.spacing16)
                ) {
                    Text(
                        text = stringResource(
                            if (!isFavorite) {
                                R.string.add_to_favorites
                            } else {
                                R.string.remove_from_favorites
                            }
                        )
                    )
                }
            }
        }) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.spacing8)
                        .fillMaxHeight(0.9f)
                        .clip(MaterialTheme.shapes.medium)
                        .background(shimmerBrush(showShimmer.value, 1000f)),
                    model = (
                            if (movie.posterPath.isEmpty() or (movie.posterPath == "https://image.tmdb.org/t/p/w500null")) {
                                R.drawable.default_image
                            } else {
                                movie.posterPath
                            }
                            ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onSuccess = {
                        showShimmer.value = false
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Text(
                    text = stringResource(R.string.le_synopsis_de_votre_film),
                    modifier = Modifier.padding(Spacing.spacing16),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Green
                )
                Text(
                    text = movie.overview,
                    modifier = Modifier.padding(Spacing.spacing16),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Text(
                    text = stringResource(R.string.la_date_de_sortie),
                    modifier = Modifier.padding(Spacing.spacing16),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Green
                )
                Text(
                    text = movie.releaseDate,
                    modifier = Modifier.padding(Spacing.spacing16),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Text(
                    text = stringResource(R.string.la_note_des_spectateurs),
                    modifier = Modifier.padding(Spacing.spacing16),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Green
                )
                Text(
                    text = movie.voteAverage.toString(),
                    modifier = Modifier.padding(Spacing.spacing16),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailContentPreview() = MovieDetailContent(
    paddingValues = PaddingValues(),
    movie = Movie(
        id = 1,
        title = "Gladiator",
        overview = "Le général romain Maximus est le plus fidèle soutien de",
        releaseDate = "2000-05-01",
        voteAverage = 8.5,
        posterPath = "https://image.tmdb.org/t/p/w500null"
    ),
    onNavigateHome = {},
    onFavoriteClick = {},
    isFavorite = false)


