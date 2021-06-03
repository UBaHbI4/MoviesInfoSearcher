package softing.ubah4ukdev.moviesinfosearcher.domain.responses

import com.google.gson.annotations.SerializedName
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.responsessuporting.MovieResponse

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.responses

Created by Ivan Sheynmaer

2021.05.30
v1.0
 */
data class ResponseMovieList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)