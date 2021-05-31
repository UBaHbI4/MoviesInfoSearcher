package softing.ubah4ukdev.moviesinfosearcher.domain.extensions

import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.MoviesRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.Response

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.extensions

Created by Ivan Sheynmaer

2021.05.31
v1.0
 */
fun ArrayList<MovieGroup>.addMovies(response: Response, groupTitle: String, posterPath: String, backdropPath: String) {
    add(
        MovieGroup(
            groupTitle,
            response.results.let {
                val movies: ArrayList<Movie> = arrayListOf()
                it.forEach {
                    movies.add(
                        Movie(
                            it.id,
                            it.title,
                            it.releaseDate,
                            "${posterPath}${it.posterPath}",
                            "${backdropPath}${it.backdropPath}",
                            it.overview
                        )
                    )
                }
                movies
            }
        )
    )
}