package apps.nb.working.pocmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_genres")
data class FavoriteGenre(
    @PrimaryKey val id: Int,
    val name: String
)
