package apps.nb.working.pocmvvm

import androidx.paging.PagingData
import app.cash.turbine.test
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.domain.usecases.GetUpcomingMoviesUseCase
import apps.nb.working.pocmvvm.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUpcomingMoviesUseCaseTest {
    private val movieRepositoryTest = mockk<MovieRepository>()
    private lateinit var getUpcomingMoviesUseCaseTest: GetUpcomingMoviesUseCase

    @Before
    fun setUp() {
        getUpcomingMoviesUseCaseTest = GetUpcomingMoviesUseCase(movieRepositoryTest)
    }

    @Test
    fun `invoke should call getUpcomingMovies from movie repository`() = runBlocking {
        // Given
        val mockPagingData = PagingData.from(
            listOf(
                Movie(
                    id = 1,
                    title = "Movie 1",
                    overview = "Overview 1",
                    posterPath = "poster1.jpg",
                    releaseDate = "2023-01-01",
                    voteAverage = 7.5
                )
            )
        )
        coEvery { movieRepositoryTest.getUpcomingMovies() } returns flowOf(mockPagingData)
        // When
        getUpcomingMoviesUseCaseTest().test {
            // Then
            val result = awaitItem()
            assertEquals(mockPagingData, result)
            awaitComplete()
        }
        // Verify that the repository method was called exactly once
        coVerify(exactly = 1) { movieRepositoryTest.getUpcomingMovies() }
    }
}
