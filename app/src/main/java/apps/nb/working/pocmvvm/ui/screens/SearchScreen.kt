package apps.nb.working.pocmvvm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import apps.nb.working.pocmvvm.R
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.ui.components.MovieListCarousel
import apps.nb.working.pocmvvm.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val searchText = remember { mutableStateOf("") }
    val moviesFlowState = viewModel.searchMovieState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_screen),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        bottomBar = { }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = {
                    searchText.value = it
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.spacing16),
                placeholder = {
                    Text(
                        text = stringResource(R.string.saisir_le_titre_du_film),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedTextColor = Color.Red,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onTertiary
                )
            )
            Button(
                onClick = {
                    viewModel.searchMovies(searchText.value)
                },
                modifier = Modifier
                    .clipToBounds()
                    .align(Alignment.CenterHorizontally)
                    .padding(Spacing.spacing16)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
                Text(text = stringResource(R.string.search))
            }
            when (moviesFlowState.value) {
                is Resource.Error -> ErrorScreen(
                    errorMessage = (moviesFlowState.value as Resource.Error).message
                )
                is Resource.Idle -> {}
                is Resource.Loading -> LoadingScreen()
                is Resource.Success -> SearchContentScreen(
                    movies = (moviesFlowState.value as Resource.Success).data.collectAsLazyPagingItems(),
                    totalResults = (moviesFlowState.value as Resource.Success).totalRecords!!,
                    onItemClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun SearchContentScreen(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Int) -> Unit,
    totalResults: Int
) {
    Column {
        MovieListCarousel(
            moviesFlow = movies,
            title = R.string.movies_found,
            totalMovies = totalResults,
            onMovieClick = onItemClick
        )
    }
}
