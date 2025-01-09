package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.domain.usecases.AddFavoriteUseCase
import apps.nb.working.pocmvvm.model.FavoriteItem
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddFavoriteUseCaseTest {
    private val favoriteRepository = mockk<FavoriteRepository>(relaxed = true)
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Before
    fun setUp() {
        addFavoriteUseCase = AddFavoriteUseCase(favoriteRepository)
    }

    @Test
    fun `AddFavoriteUseCase should call favoriteRepository insert with correct FavoriteItem`() = runBlocking {
        // Given
        val testItemId = 123
        // When
        addFavoriteUseCase.invoke(testItemId)
        // Then
        coVerify { favoriteRepository.insert(FavoriteItem(movieId = testItemId)) }
    }
}
