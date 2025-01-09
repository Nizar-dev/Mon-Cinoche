package apps.nb.working.pocmvvm.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import apps.nb.working.pocmvvm.model.MoviesTab
import apps.nb.working.pocmvvm.ui.screens.MainViewModel
import apps.nb.working.pocmvvm.ui.theme.Spacing

@Composable
internal fun MyBottomBar(
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    viewmodel: MainViewModel
) {
    val selectedTab = viewmodel.selectedTab.collectAsStateWithLifecycle()
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.Red, Color.Black)
    )
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)

        ) {
            drawRect(gradientBrush)
        }
        NavigationBar(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            tonalElevation = Spacing.spacing6,
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ) {
            MoviesTab.entries.forEach { tab ->
                NavigationBarItem(
                    selected = selectedTab.value == tab,
                    onClick = {
                        when (tab) {
                            MoviesTab.HOME -> onNavigateToHome()
                            MoviesTab.SEARCH -> onNavigateToSearch()
                            MoviesTab.FAVORITES -> onNavigateToFavorite()
                        }
                        viewmodel.onTabSelected(tab)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab.value == tab) {
                                tab.selectedIcon
                            } else {
                                tab.unselectedIcon
                            },
                            contentDescription = tab.name,
                            tint = if (selectedTab.value == tab) {
                                Color.White
                            } else {
                                Color.White.copy(alpha = 0.5f)
                            }
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
