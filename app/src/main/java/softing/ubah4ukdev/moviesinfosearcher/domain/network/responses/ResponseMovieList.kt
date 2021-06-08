package softing.ubah4ukdev.moviesinfosearcher.domain.network.responses

import com.google.gson.annotations.SerializedName

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.network.responses

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