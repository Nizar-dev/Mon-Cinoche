package apps.nb.working.pocmvvm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.ui.theme.Spacing

@Composable
internal fun MovieListCarousel(
    title: Int,
    totalMovies: Int = 0,
    moviesFlow: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier
                .padding(Spacing.spacing8)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = title, totalMovies),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        LazyRow(
            modifier = Modifier.padding(Spacing.spacing8),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing16)
        ) {
            items(moviesFlow.itemCount) { index ->
                val movie = moviesFlow[index]
                movie?.let {
                    MovieItem(movie = it, onMovieClick = onMovieClick)
                }
            }
        }
    }
}
