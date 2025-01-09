package apps.nb.working.pocmvvm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apps.nb.working.pocmvvm.ui.theme.Spacing

@Composable
fun GenreItem(
    modifier: Modifier ,
    genre: String,
    hasButton: Boolean = false,
    deleteAction: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(vertical = Spacing.spacing8)
            .width(200.dp)
            .height(50.dp)
            .background(
                Color.LightGray, shape = MaterialTheme.shapes.medium
            )
    ) {

                Text(
                    text = genre,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(Spacing.spacing4)
                        .align(Alignment.BottomCenter)
                )
                if (hasButton) {
                    IconButton(
                        onClick = {
                            deleteAction()
                        }, modifier = Modifier
                            .padding(end = Spacing.spacing4)
                            .offset((-10).dp, (-10).dp)
                    ) {
                        Icon(Icons.Filled.Clear, contentDescription = "Delete")
                    }
                }


    }
}

@Composable
@Preview(showBackground = true)
fun GenreItemPreview() {
    GenreItem(genre = "Documentary", hasButton = true, modifier = Modifier)
}

