package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.model.FavoriteGenre
import javax.inject.Inject

class UpdateFavoriteGenresUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(genres: List<FavoriteGenre>) {
        repository.updateGenres(genres)
    }
}
