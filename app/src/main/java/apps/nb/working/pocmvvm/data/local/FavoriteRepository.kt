package apps.nb.working.pocmvvm.data.local

import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.FavoriteItem

interface FavoriteRepository {
    /**
     * Favorites Movies
     */
    // suspend fun getFavoriteItem(id: Int): List<FavoriteItem>
    suspend fun insert(item: FavoriteItem)
    suspend fun delete(movieId: Int)
    suspend fun getAllFavorites(): List<FavoriteItem>

    /**
     * Favorites Genres
     */
    // get all favorite genres
    suspend fun getAllGenres(): List<FavoriteGenre>

    // update favorite genres
    suspend fun updateGenres(genres: List<FavoriteGenre>)
}
