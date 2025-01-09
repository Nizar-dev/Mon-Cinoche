package apps.nb.working.pocmvvm.data.local

import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.FavoriteItem
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val itemDao: FavoriteItemDao,
    private val favoriteGenreDao: FavoriteGenreDao
) : FavoriteRepository {
    /**
     * Favorite Movies Methods
     */
//    override suspend fun getFavoriteItem(id: Int): List<FavoriteItem> {
//        TODO("Not yet implemented")
//    }

    override suspend fun insert(item: FavoriteItem) {
        itemDao.insert(item)
    }

    override suspend fun delete(movieId: Int) {
        itemDao.delete(movieId)
    }

    override suspend fun getAllFavorites(): List<FavoriteItem> {
        return itemDao.getAllFavorites()
    }

    /**
     * Favorite Genres Methods
     */
    override suspend fun getAllGenres(): List<FavoriteGenre> {
        return favoriteGenreDao.getAllFavoriteGenres()
    }

    override suspend fun updateGenres(genres: List<FavoriteGenre>) {
        val currentGenres = getAllGenres()
        currentGenres.forEach() { genre ->
            if (!genres.contains(genre)) {
                favoriteGenreDao.deleteFavoriteGenre(genre)
            }
        }
        genres.forEach() { genre ->
            if (!currentGenres.contains(genre)) {
                favoriteGenreDao.insertFavoriteGenre(genre)
            }
        }
    }
}
