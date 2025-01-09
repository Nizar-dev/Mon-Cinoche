package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.model.FavoriteGenre
import javax.inject.Inject

class GetFavoriteGenresUseCase @Inject constructor(
    private val favoriteGenresRepository: FavoriteRepository
) {
    suspend operator fun invoke(): List<FavoriteGenre> {
        return favoriteGenresRepository.getAllGenres()
    }
}
