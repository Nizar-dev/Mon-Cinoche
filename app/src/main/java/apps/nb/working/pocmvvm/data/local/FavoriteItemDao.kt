package apps.nb.working.pocmvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apps.nb.working.pocmvvm.model.FavoriteItem

@Dao
interface FavoriteItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteItem)

    @Query(value = "DELETE FROM Favorites WHERE movieId = :movieId")
    suspend fun delete(movieId: Int)

    @Query(value = "SELECT * FROM Favorites")
    suspend fun getAllFavorites(): List<FavoriteItem>
}
