package apps.nb.working.pocmvvm.data.remote

import apps.nb.working.pocmvvm.model.Movie
import apps.nb.working.pocmvvm.model.MovieGenre

fun convertApiResponseToMovies(apiResponse: MovieApiResponse): List<Movie> {
    return apiResponse.results.map { movieResult ->
        Movie(
            id = movieResult.id,
            title = movieResult.title,
            overview = movieResult.overview,
            posterPath = "https://image.tmdb.org/t/p/w500${movieResult.posterPath}",
            releaseDate = movieResult.releaseDate,
            voteAverage = movieResult.voteAverage
        )
    }
}

fun convertApiResponseToMovie(movieResult: MovieResult): Movie {
    return Movie(
        id = movieResult.id,
        title = movieResult.title,
        overview = movieResult.overview,
        posterPath = "https://image.tmdb.org/t/p/w500${movieResult.posterPath}",
        releaseDate = movieResult.releaseDate,
        voteAverage = movieResult.voteAverage
    )
}

fun convertApiResponseGenreToGenres(apiResponse: GenreApiResponse): List<MovieGenre> {
    return apiResponse.genres.map { genreResult ->
        MovieGenre(
            id = genreResult.id,
            name = genreResult.name
        )
    }
}
