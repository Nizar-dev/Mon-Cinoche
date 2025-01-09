package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.domain.SearchRepository
import apps.nb.working.pocmvvm.domain.usecases.SearchUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest {
    private val searchRepositoryTest = mockk<SearchRepository>(relaxed = true)
    private lateinit var searchUseCaseTest: SearchUseCase

    @Before
    fun setUp() {
        searchUseCaseTest = SearchUseCase(searchRepositoryTest)
    }

    @Test
    fun `invoke should call searchRepository getMoviesResult with correct string`() = runBlocking {
        // Given
        val testQueryTitle = "Paris"
        // When
        searchUseCaseTest(testQueryTitle)
        // Then
        coVerify(exactly = 1) { searchRepositoryTest.getMoviesResult(testQueryTitle) }
    }
}
