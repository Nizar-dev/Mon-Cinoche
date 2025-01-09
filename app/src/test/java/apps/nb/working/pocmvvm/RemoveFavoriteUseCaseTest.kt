package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.domain.usecases.RemoveFavoriteUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoveFavoriteUseCaseTest {
    private val favoriteRepositoryTest = mockk<FavoriteRepository>(relaxed = true)
    private lateinit var removeFavoriteUseCaseTest: RemoveFavoriteUseCase

    @Before
    fun setUp() {
        removeFavoriteUseCaseTest = RemoveFavoriteUseCase(favoriteRepositoryTest)
    }

    @Test
    fun `invoke should call favoriteRepository delete with correct itemId`() = runBlocking {
        // Given
        val testItemId = 123
        // When
        removeFavoriteUseCaseTest(testItemId)
        // Then
        coVerify(exactly = 1) { favoriteRepositoryTest.delete(testItemId) }
    }
}
