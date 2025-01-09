package apps.nb.working.pocmvvm.di

import android.content.Context
import apps.nb.working.pocmvvm.data.local.CinocheDataBase
import apps.nb.working.pocmvvm.data.local.FavoriteGenreDao
import apps.nb.working.pocmvvm.data.local.FavoriteItemDao
import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.data.local.FavoriteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext application: Context): CinocheDataBase {
        return CinocheDataBase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun providesFavoriteDao(database: CinocheDataBase): FavoriteItemDao {
        return database.favoriteItemDao()
    }

    @Provides
    @Singleton
    fun providesFavoriteGenreDao(database: CinocheDataBase): FavoriteGenreDao {
        return database.favoriteGenreDao()
    }

    @Provides
    @Singleton
    fun providesFavoriteRepository(favoriteDao: FavoriteItemDao, favoriteGenreDao: FavoriteGenreDao): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao, favoriteGenreDao)
    }
}
