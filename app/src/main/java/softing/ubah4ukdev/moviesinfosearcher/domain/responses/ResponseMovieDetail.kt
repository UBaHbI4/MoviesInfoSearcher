package softing.ubah4ukdev.moviesinfosearcher.domain.responses

import com.google.gson.annotations.SerializedName
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.responsessuporting.Genres
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.responsessuporting.ProductionCompanies
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.responsessuporting.ProductionCountries

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.responses

Created by Ivan Sheynmaer

2021.05.30
v1.0
 */
data class ResponseMovieDetail(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genres>,
    @SerializedName("homepage")
    val homePage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountries>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
)