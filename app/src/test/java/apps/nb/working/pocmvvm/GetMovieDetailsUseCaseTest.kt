package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.domain.usecases.GetMovieDetailsUseCase
import apps.nb.working.pocmvvm.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {
    private val movieRepositoryTest = mockk<MovieRepository>()
    private lateinit var getMovieDetailsUseCaseTest: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        getMovieDetailsUseCaseTest = GetMovieDetailsUseCase(movieRepositoryTest)
    }

    @Test
    fun `the use case should call the repository's getMovieById method`() = runBlocking {
        // Given
        val idTest = 123
        val movieTest = Movie(
            id = 123,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "poster1.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 7.5
        )
        coEvery { movieRepositoryTest.getMovieById(idTest) } returns Resource.Success(movieTest)

        // When
        val result = getMovieDetailsUseCaseTest(idTest)
        // Then
        assert(result is Resource.Success)
        assert((result as Resource.Success).data == movieTest)
        coVerify(exactly = 1) { movieRepositoryTest.getMovieById(idTest) }
    }
}
