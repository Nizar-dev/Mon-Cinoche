package apps.nb.working.pocmvvm.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(
    val movieId: Int
)

@Serializable
object FavoritesDestination

@Serializable
object PreferencesDestination

@Serializable
object SearchDestination

@Serializable
object LostConnectionDestination
