package apps.nb.working.pocmvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apps.nb.working.pocmvvm.model.FavoriteGenre

@Dao
interface FavoriteGenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGenre(genre: FavoriteGenre)

    @Delete
    suspend fun deleteFavoriteGenre(genre: FavoriteGenre)

    @Query("SELECT * FROM favorite_genres")
    suspend fun getAllFavoriteGenres(): List<FavoriteGenre>
}
