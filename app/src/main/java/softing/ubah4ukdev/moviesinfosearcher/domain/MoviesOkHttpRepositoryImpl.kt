package softing.ubah4ukdev.moviesinfosearcher.domain

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import softing.ubah4ukdev.moviesinfosearcher.BuildConfig
import softing.ubah4ukdev.moviesinfosearcher.domain.extensions.addMovies
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.ResponseMovieDetail
import softing.ubah4ukdev.moviesinfosearcher.domain.responses.ResponseMovieList
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
//Репозиторий, с загрузкой данных через OkHttp
object MoviesOkHttpRepositoryImpl : IMovieRepository {
    private const val URL_UPCOMING =
        "https://api.themoviedb.org/3/movie/upcoming?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1"
    private const val URL_TOP_RATED =
        "https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1"
    private const val URL_POPULAR =
        "https://api.themoviedb.org/3/movie/popular?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1"
    private const val URL_NOW_PLAYING =
        "https://api.themoviedb.org/3/movie/now_playing?api_key=${BuildConfig.API_KEY}&language=ru-RU&page=1"

    private const val URL_MOVIE_DETAIL = "https://api.themoviedb.org/3/movie/"

    private const val UPCOMING_TITLE = "Up Coming"
    private const val TOP_RATED_TITLE = "Top rated"
    private const val POPULAR_TITLE = "Popular"
    private const val NOW_PLAYING_TITLE = "Now playing"

    private const val QUERY_LNG = "&language=ru-RU"
    private const val QUERY_API = "?api_key="

    private const val URL_POSTER_PATH = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    private const val URL_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

    private const val HEADER_EXAMPLE = "testHeader"
    private const val HEADER_VALUE_EXAMPLE = "testHeaderValue"

    private val executor: Executor = Executors.newCachedThreadPool()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    /*
    *  В данном методе попробуем вызов через call.execute()
    */
    override fun getMovies(callback: (result: RepositoryResult<ArrayList<MovieGroup>>) -> Unit) {
        executor.execute {
            val gson = Gson()
            val movieGroups: ArrayList<MovieGroup> = ArrayList()

            val client = OkHttpClient.Builder().apply {
                addInterceptor(AddHeaderInterceptor())

                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
                .build()
            var request = Request.Builder().apply {
                addHeader(HEADER_EXAMPLE, HEADER_VALUE_EXAMPLE)
                url(URL_POPULAR)
            }
                .build()
            var call = client.newCall(request)
            var response: Response = call.execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.bufferedReader()?.let { reader ->
                    fun response(): ResponseMovieList = gson.fromJson(
                        reader,
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        POPULAR_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            request = Request.Builder().apply {
                addHeader(HEADER_EXAMPLE, HEADER_VALUE_EXAMPLE)
                url(URL_NOW_PLAYING)
            }
                .build()
            call = client.newCall(request)
            response = call.execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.bufferedReader()?.let { reader ->
                    fun response(): ResponseMovieList = gson.fromJson(
                        reader,
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        NOW_PLAYING_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            request = Request.Builder().apply {
                addHeader(HEADER_EXAMPLE, HEADER_VALUE_EXAMPLE)
                url(URL_UPCOMING)
            }
                .build()
            call = client.newCall(request)
            response = call.execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.bufferedReader()?.let { reader ->
                    fun response(): ResponseMovieList = gson.fromJson(
                        reader,
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        UPCOMING_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            request = Request.Builder().apply {
                addHeader(HEADER_EXAMPLE, HEADER_VALUE_EXAMPLE)
                url(URL_TOP_RATED)
            }
                .build()
            call = client.newCall(request)
            response = call.execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.bufferedReader()?.let { reader ->
                    fun response(): ResponseMovieList = gson.fromJson(
                        reader,
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        TOP_RATED_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            mainThreadHandler.post {
                callback.invoke(Success(movieGroups))
            }
        }
    }

    /*
    *  В данном методе попробуем вызов через call.enqueue, с callback-ом, ассинхронный вызов
    */
    //Метод получения подробной информации о фильме
    override fun getMovieDetail(
        movieID: Int,
        movie: Movie,
        callback: (result: RepositoryResult<Movie>) -> Unit
    ) {
        val gson = Gson()

        val client = OkHttpClient.Builder().apply {
            addInterceptor(AddHeaderInterceptor())

            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()
        val request = Request.Builder().apply {
            addHeader(HEADER_EXAMPLE, HEADER_VALUE_EXAMPLE)
            url("${URL_MOVIE_DETAIL}${movie.id}$QUERY_API${BuildConfig.API_KEY}$QUERY_LNG")
        }
            .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mainThreadHandler.post {
                    callback.invoke(Error(e))
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.byteStream()?.bufferedReader()?.let { reader ->

                        fun response(): ResponseMovieDetail = gson.fromJson(
                            reader,
                            ResponseMovieDetail::class.java
                        )

                        val movieDetail: Movie = response().let {
                            val movieResult: Movie = movie.copy(
                                budget = it.budget,
                                genres = it.genres,
                                homePage = it.homePage,
                                imdbId = it.imdbId,
                                productionCompanies = it.productionCompanies,
                                productionCountries = it.productionCountries,
                                revenue = it.revenue,
                                runtime = it.runtime,
                                status = it.status,
                                tagline = it.tagline
                            )
                            movieResult
                        }
                        mainThreadHandler.post {
                            callback.invoke(Success(movieDetail))
                        }
                    }
                } else {
                    mainThreadHandler.post {
                        callback.invoke(Error(Exception(response.code.toString())))
                    }
                }
            }
        })
    }
}