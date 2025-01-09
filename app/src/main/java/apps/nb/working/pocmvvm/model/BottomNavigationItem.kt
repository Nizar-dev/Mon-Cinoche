package apps.nb.working.pocmvvm.model

import androidx.compose.ui.graphics.vector.ImageVector

// Bottom Navigation Item
data class BottomNavigationItem(
    val titre: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
