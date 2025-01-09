package apps.nb.working.pocmvvm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import apps.nb.working.pocmvvm.R

@Composable
fun LostConnectionScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.lost_connexion),
            contentDescription = "Connection Error",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
        Text(
            text = stringResource(R.string.connexion_lost),
            color = androidx.compose.ui.graphics.Color.White,
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LostConnectionScreenPreview() {
    LostConnectionScreen()
}
