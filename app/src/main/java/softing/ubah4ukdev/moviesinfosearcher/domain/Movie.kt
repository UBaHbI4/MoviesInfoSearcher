package softing.ubah4ukdev.moviesinfosearcher.domain

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.04.30
v1.0
 */
data class Movie(
    val id: Int,
    val title: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?
)
