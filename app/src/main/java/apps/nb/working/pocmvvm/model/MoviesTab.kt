package apps.nb.working.pocmvvm.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import apps.nb.working.pocmvvm.R

enum class MoviesTab(
    val titleResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(titleResId = R.string.accueil, Icons.Filled.Home, Icons.Outlined.Home),
    SEARCH(titleResId = R.string.search, Icons.Filled.Search, Icons.Outlined.Search),
    FAVORITES(titleResId = R.string.favorites, Icons.Filled.Favorite, Icons.Outlined.Favorite)
}
