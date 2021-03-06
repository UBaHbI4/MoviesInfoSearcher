package softing.ubah4ukdev.moviesinfosearcher.domain.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import softing.ubah4ukdev.moviesinfosearcher.BuildConfig
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ResponseMovieDetail
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ResponseMovieList

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.net.responses

Created by Ivan Sheynmaer

2021.06.08
v1.0
 */
interface ITheMovieDbApi {
    @GET("upcoming?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1")
    fun getMoviesUpcoming(
        @Query("include_adult") adult: Boolean
    ): Call<ResponseMovieList>

    @GET("top_rated?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1")
    fun getMoviesTopRated(
        @Query("include_adult") adult: Boolean
    ): Call<ResponseMovieList>

    @GET("popular?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1")
    fun getMoviesPopular(
        @Query("include_adult") adult: Boolean
    ): Call<ResponseMovieList>

    @GET("now_playing?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1")
    fun getMoviesNowPlaying(
        @Query("include_adult") adult: Boolean
    ): Call<ResponseMovieList>

    @GET("{movieid}?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1")
    fun getMoviesDetail(@Path("movieid") movieID: Int): Call<ResponseMovieDetail>
}