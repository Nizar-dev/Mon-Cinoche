package apps.nb.working.pocmvvm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Dégradé rouge et noir pour un look cinéma
private val RedDark = Color(0xFFB71C1C) // Rouge profond
private val DarkBackground = Color(0xFF121212) // Noir profond
private val GrayBackground = Color(0xFF1C1C1C) // Gris anthracite pour thème sombre
private val LightBackground = Color(0xFF202020) // Gris très clair pour le thème clair
private val AccentRed = Color(0xFFD32F2F) // Rouge plus vif pour les éléments sélectionnés

// Thème clair avec une base plus douce
private val lightScheme = lightColorScheme(
    primary = AccentRed,
    onPrimary = Color.White,
    background = LightBackground,
    onBackground = Color.Black,
    surface = LightBackground,
    onSurface = Color.Black,
    error = Color.Red,
    onError = Color.White,
    secondary = AccentRed,
    onSecondary = Color.White
)

// Thème sombre avec noir et rouge prédominant
private val darkScheme = darkColorScheme(
    primary = RedDark,
    onPrimary = Color.White,
    background = DarkBackground,
    onBackground = Color.White,
    surface = GrayBackground,
    onSurface = Color.White,
    error = Color.Red,
    onError = Color.White,
    secondary = RedDark
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified
)

@Composable
fun POCMVVMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colorScheme = if (darkTheme) darkScheme else lightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
