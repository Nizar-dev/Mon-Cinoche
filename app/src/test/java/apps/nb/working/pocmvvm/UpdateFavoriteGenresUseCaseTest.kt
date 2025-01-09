package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.domain.usecases.UpdateFavoriteGenresUseCase
import apps.nb.working.pocmvvm.model.FavoriteGenre
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateFavoriteGenresUseCaseTest {
    private val favoriteRepositoryTest = mockk<FavoriteRepository>(relaxed = true)
    private lateinit var updateFavoriteGenresUseCaseTest: UpdateFavoriteGenresUseCase

    @Before
    fun setUp() {
        updateFavoriteGenresUseCaseTest = UpdateFavoriteGenresUseCase(favoriteRepositoryTest)
    }

    @Test
    fun `updateFavoriteGenresUseCase should call favoriteRepository updateGenres with correct FavoriteGenre`() = runBlocking {
        // Given
        val genresList = listOf(FavoriteGenre(id = 1, name = "Action"))
        // When
        updateFavoriteGenresUseCaseTest.invoke(genresList)
        // Then
        coVerify { favoriteRepositoryTest.updateGenres(genresList) }
    }
}
