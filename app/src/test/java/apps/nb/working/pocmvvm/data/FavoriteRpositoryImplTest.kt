package apps.nb.working.pocmvvm.data

import apps.nb.working.pocmvvm.data.local.FavoriteGenreDao
import apps.nb.working.pocmvvm.data.local.FavoriteItemDao
import apps.nb.working.pocmvvm.data.local.FavoriteRepositoryImpl
import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.FavoriteItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FavoriteRepositoryImplTest {
    private val favoriteItemDaoTest = mockk<FavoriteItemDao>(relaxed = true)
    private val favoriteGenreDaoTest = mockk<FavoriteGenreDao>(relaxed = true)
    private lateinit var favoriteRepositoryTest: FavoriteRepositoryImpl

    @Before
    fun setUp() {
        favoriteRepositoryTest = FavoriteRepositoryImpl(favoriteItemDaoTest, favoriteGenreDaoTest)
    }

    @Test
    fun `insert should call favoriteItemDao insert`() = runBlocking {
        // Given
        val testFavoriteItemTest = mockk<FavoriteItem> (relaxed = true)
        // When
        favoriteRepositoryTest.insert(testFavoriteItemTest)
        // Then
        coVerify(exactly = 1) { favoriteItemDaoTest.insert(testFavoriteItemTest) }
    }

    @Test
    fun `delete should call favoriteItemDao delete`() = runBlocking {
        // Given
        val itemIdTest = 123
        // When
        favoriteRepositoryTest.delete(itemIdTest)
        // Then
        coVerify(exactly = 1) { favoriteItemDaoTest.delete(itemIdTest) }
    }

    @Test
    fun `getAllFavorites should call favoriteItemDao getAllFavorites`() = runBlocking {
        // Given
        val favoriteItemsTest = mockk<List<FavoriteItem>>(relaxed = true)
        coEvery { favoriteItemDaoTest.getAllFavorites() } returns favoriteItemsTest
        // When
        val result = favoriteRepositoryTest.getAllFavorites()
        // Then
        coVerify(exactly = 1) { favoriteItemDaoTest.getAllFavorites() }
        assert(result == favoriteItemsTest)
    }

    @Test
    fun `getAllGenres should call favoriteGenreDao getAllFavoriteGenres`() = runBlocking {
        // Given
        val favoriteGenresTest = mockk<List<FavoriteGenre>>(relaxed = true)
        coEvery { favoriteGenreDaoTest.getAllFavoriteGenres() } returns favoriteGenresTest
        // When
        val result = favoriteRepositoryTest.getAllGenres()
        // Then
        coVerify(exactly = 1) { favoriteGenreDaoTest.getAllFavoriteGenres() }
        assert(result == favoriteGenresTest)
    }

    @Test
    fun `updateGenres should call favoriteGenreDao insertFavoriteGenre and deleteFavoriteGenre`() = runBlocking {
        // Given
        val genresListTest = listOf(
            FavoriteGenre(id = 1, name = "Action"),
            FavoriteGenre(id = 2, name = "Comedy")
        )
        val currentGenresTest = listOf(
            FavoriteGenre(id = 1, name = "Action"),
            FavoriteGenre(id = 3, name = "Drama")
        )
        coEvery { favoriteGenreDaoTest.getAllFavoriteGenres() } returns currentGenresTest
        // When
        favoriteRepositoryTest.updateGenres(genresListTest)
        // Then
        coVerify { favoriteGenreDaoTest.getAllFavoriteGenres() }
        coVerify { favoriteGenreDaoTest.insertFavoriteGenre(any()) }
    }
}
