package softing.ubah4ukdev.moviesinfosearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.Genres
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ProductionCompanies
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ProductionCountries

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.04.30
v1.0
 */
@Parcelize
data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genres_ids: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var budget: Int = 0,
    val genres: List<Genres> = arrayListOf(),
    val homePage: String = "",
    val imdbId: String = "",
    val productionCompanies: List<ProductionCompanies> = arrayListOf(),
    val productionCountries: List<ProductionCountries> = arrayListOf(),
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    var comment: String = ""
) : Parcelable