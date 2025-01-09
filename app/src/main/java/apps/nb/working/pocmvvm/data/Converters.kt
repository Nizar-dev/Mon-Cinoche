package apps.nb.working.pocmvvm.data

import apps.nb.working.pocmvvm.model.FavoriteGenre
import apps.nb.working.pocmvvm.model.MovieGenre

fun MovieGenre.toFavoriteGenre(): FavoriteGenre {
    return FavoriteGenre(id = this.id, name = this.name)
}

fun FavoriteGenre.toMovieGenre(): MovieGenre {
    return MovieGenre(id = this.id, name = this.name)
}
