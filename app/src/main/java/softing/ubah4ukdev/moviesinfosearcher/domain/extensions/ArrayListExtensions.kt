package softing.ubah4ukdev.moviesinfosearcher.domain.extensions

import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.ResponseMovieList

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.extensions

Created by Ivan Sheynmaer

2021.05.31
v1.0
 */
fun ArrayList<MovieGroup>.addMovies(
    responseMovieList: ResponseMovieList,
    groupTitle: String,
    posterPath: String,
    backdropPath: String
) {
    add(
        MovieGroup(
            groupTitle,
            responseMovieList.results.let {
                val movies: ArrayList<Movie> = arrayListOf()
                it.forEach {
                    movies.add(
                        Movie(
                            it.adult,
                            "${backdropPath}${it.backdropPath}",
                            it.genreIds,
                            it.id,
                            it.originalLanguage,
                            it.originalTitle,
                            it.overview,
                            it.popularity,
                            "${posterPath}${it.posterPath}",
                            it.releaseDate,
                            it.title,
                            it.video,
                            it.voteAverage,
                            it.voteCount
                        )
                    )
                }
                movies
            }
        )
    )
}