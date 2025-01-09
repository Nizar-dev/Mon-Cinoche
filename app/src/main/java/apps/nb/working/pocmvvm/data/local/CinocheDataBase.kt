package apps.nb.working.pocmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.FavoriteItem

@Database(entities = [FavoriteItem::class, FavoriteGenre::class], version = 2, exportSchema = false)
abstract class CinocheDataBase : RoomDatabase() {
    abstract fun favoriteItemDao(): FavoriteItemDao
    abstract fun favoriteGenreDao(): FavoriteGenreDao
    companion object {
        @Volatile
        private var INSTANCE: CinocheDataBase? = null
        fun getDatabase(context: Context): CinocheDataBase {
            val migration12 = object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("CREATE TABLE `favorite_genres` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
                }
            }
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CinocheDataBase::class.java,
                    "cinoche_database"
                )
                    .addMigrations(migration12).build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}
