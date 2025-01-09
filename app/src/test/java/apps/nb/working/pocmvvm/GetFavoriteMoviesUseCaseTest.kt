package apps.nb.working.pocmvvm

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.data.remote.Resource
import apps.nb.working.pocmvvm.domain.MovieRepository
import apps.nb.working.pocmvvm.domain.usecases.GetFavoriteMoviesUseCase
import apps.nb.working.pocmvvm.model.FavoriteItem
import apps.nb.working.pocmvvm.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetFavoriteMoviesUseCaseTest {

    private val mockFavoriteRepository = mockk<FavoriteRepository>()
    private val mockMovieRepository = mockk<MovieRepository>()
    private lateinit var getFavoriteMoviesUseCaseTest: GetFavoriteMoviesUseCase

    @Before
    fun setUp() {
        getFavoriteMoviesUseCaseTest = GetFavoriteMoviesUseCase(mockFavoriteRepository, mockMovieRepository)
    }

    @Test
    fun `invoke should return movies for all valid favorite items`() = runBlocking {
        // Given
        val favoriteItems = listOf(
            FavoriteItem(movieId = 1),
            FavoriteItem(movieId = 2)
        )
        val movie1 = Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "poster1.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 7.5
        )
        val movie2 = Movie(
            id = 2,
            title = "Movie 2",
            overview = "Overview 2",
            posterPath = "poster2.jpg",
            releaseDate = "2023-02-01",
            voteAverage = 8.0
        )

        coEvery { mockFavoriteRepository.getAllFavorites() } returns favoriteItems
        coEvery { mockMovieRepository.getMovieById(1) } returns Resource.Success(movie1)
        coEvery { mockMovieRepository.getMovieById(2) } returns Resource.Success(movie2)

        // When
        val result = getFavoriteMoviesUseCaseTest()

        // Then
        assertEquals(listOf(movie1, movie2), result)
        coVerify(exactly = 1) { mockFavoriteRepository.getAllFavorites() }
        coVerify(exactly = 1) { mockMovieRepository.getMovieById(1) }
        coVerify(exactly = 1) { mockMovieRepository.getMovieById(2) }
    }

    @Test
    fun `the use case should handle the case where a movie is not found`() = runBlocking {
        // Given
        val favoriteItems = listOf(
            FavoriteItem(movieId = 1),
            FavoriteItem(movieId = 2)
        )
        val movie1 = Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "poster1.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 7.5
        )

        coEvery { mockFavoriteRepository.getAllFavorites() } returns favoriteItems
        coEvery { mockMovieRepository.getMovieById(1) } returns Resource.Success(movie1)
        coEvery { mockMovieRepository.getMovieById(2) } returns Resource.Error("Not Found")

        // When
        val result = getFavoriteMoviesUseCaseTest()

        // Then
        assertEquals(listOf(movie1), result)
        coVerify(exactly = 1) { mockFavoriteRepository.getAllFavorites() }
        coVerify(exactly = 1) { mockMovieRepository.getMovieById(1) }
        coVerify(exactly = 1) { mockMovieRepository.getMovieById(2) }
    }

    @Test
    fun `invoke should return empty list if no favorites are found`() = runBlocking {
        // Given
        coEvery { mockFavoriteRepository.getAllFavorites() } returns emptyList()

        // When
        val result = getFavoriteMoviesUseCaseTest()

        // Then
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { mockFavoriteRepository.getAllFavorites() }
        coVerify(exactly = 0) { mockMovieRepository.getMovieById(any()) }
    }
}
