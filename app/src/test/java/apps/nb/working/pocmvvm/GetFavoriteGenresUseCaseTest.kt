package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.domain.usecases.GetFavoriteGenresUseCase
import apps.nb.working.pocmvvm.model.FavoriteGenre
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMovieGenresUseCaseTest {
    private val favoriteGenresRepositoryTest = mockk<FavoriteRepository>()
    private lateinit var getFavoriteGenresUseCaseTest: GetFavoriteGenresUseCase

    @Before
    fun setUp() {
        getFavoriteGenresUseCaseTest = GetFavoriteGenresUseCase(favoriteGenresRepositoryTest)
    }

    @Test
    fun `invoke should get getAllGenres from favoriteGenresRepository`() = runBlocking {
        // Given
        val mockGenres = listOf(
            FavoriteGenre(1, "Action"),
            FavoriteGenre(2, "Comedy"),
            FavoriteGenre(3, "Drama")
        )
        coEvery { favoriteGenresRepositoryTest.getAllGenres() } returns mockGenres
        // When
        val result = getFavoriteGenresUseCaseTest()
        // Then
        assertEquals(mockGenres, result)
        coVerify(exactly = 1) { favoriteGenresRepositoryTest.getAllGenres() }
    }
}
