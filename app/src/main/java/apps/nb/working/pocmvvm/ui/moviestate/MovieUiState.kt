package apps.nb.working.pocmvvm.ui.moviestate

import apps.nb.working.pocmvvm.model.Movie

data class MovieUiState(
    val popularMoviesList: List<Movie> = emptyList(), // Liste des films populaires
    val searchQuery: String = "", // titre recherché
    val resultQueryList: List<Movie> = emptyList(), // Liste des films correspondant à la recherche
    val movieDetails: Movie? = null // details du film sélectionné
)
