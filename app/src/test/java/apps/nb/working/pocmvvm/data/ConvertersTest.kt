package apps.nb.working.pocmvvm.data

import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.MovieGenre
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun `MovieGenre to FavoriteGenre should correctly map fields`() {
        // Given
        val movieGenre = MovieGenre(id = 1, name = "Action")
        // When
        val favoriteGenre = movieGenre.toFavoriteGenre()
        // Then
        assert(favoriteGenre.id == movieGenre.id)
        assert(favoriteGenre.name == movieGenre.name)
    }

    @Test
    fun `FavoriteGenre to MovieGenre should correctly map fields`() {
        // Given
        val favoriteGenre = FavoriteGenre(id = 2, name = "Comedy")

        // When
        val movieGenre = favoriteGenre.toMovieGenre()

        // Then
        assertEquals(2, movieGenre.id)
        assertEquals("Comedy", movieGenre.name)
    }
}
