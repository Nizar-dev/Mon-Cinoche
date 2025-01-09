package apps.nb.working.pocmvvm.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apps.nb.working.pocmvvm.R
import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.ui.theme.Spacing
import coil.compose.AsyncImage

@SuppressLint("DefaultLocale")
@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    sizeOfImage: Int = 500
) {
    val width = (sizeOfImage * 0.5).toInt()
    val showShimmer = remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .height(sizeOfImage.dp)
            .width(width.dp)
            .clickable {
                onMovieClick(movie.id)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(Spacing.spacing8)
                    .align(Alignment.CenterHorizontally)
            )
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
            Row(
                modifier = Modifier
                    .padding(Spacing.spacing8)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = movie.releaseDate
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.spacing4)
                ) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null)
                    Text(text = String.format("%.1f", movie.voteAverage))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieItemPreview() {
    MovieItem(
        movie = Movie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "https://picsum.photos/id/6/5000/3333",
            releaseDate = "2023-01-01",
            voteAverage = 2.8
        ),
        onMovieClick = {}
    )
}
