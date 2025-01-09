package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import javax.inject.Inject

class RemoveMovieFromFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int) {
        favoriteRepository.delete(movieId)
    }
}
