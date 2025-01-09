package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import apps.nb.working.pocmvvm.model.FavoriteItem
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favRepository: FavoriteRepository
) {
    suspend operator fun invoke(itemId: Int) {
        val favoriteItem = FavoriteItem(movieId = itemId)
        favRepository.insert(favoriteItem)
    }
}
