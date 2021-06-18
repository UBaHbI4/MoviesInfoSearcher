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
data class MovieHistory(
    val histID : Int,
    val date :Long,
    val adult: Boolean,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val revenue: Int = 0,
    val runtime: Int = 0,
    var comment: String = ""
) : Parcelable