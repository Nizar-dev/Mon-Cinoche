package apps.nb.working.pocmvvm.domain.usecases

import apps.nb.working.pocmvvm.data.local.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val favRepository: FavoriteRepository
) {
    suspend operator fun invoke(itemId: Int) {
        favRepository.delete(itemId)
    }
}
