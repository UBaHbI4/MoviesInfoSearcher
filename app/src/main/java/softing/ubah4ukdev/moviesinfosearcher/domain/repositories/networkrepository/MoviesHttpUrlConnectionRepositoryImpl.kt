package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import softing.ubah4ukdev.moviesinfosearcher.BuildConfig
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.RepositoryResult
import softing.ubah4ukdev.moviesinfosearcher.domain.Success
import softing.ubah4ukdev.moviesinfosearcher.domain.extensions.addMovies
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ResponseMovieDetail
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ResponseMovieList
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
//Репозиторий, с загрузкой данных через HttpUrlConnection
object MoviesHttpUrlConnectionRepositoryImpl : IMovieRepository {
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

    private const val REQUEST_METHOD_GET = "GET"

    private const val QUERY_LNG = "&language=ru-RU"
    private const val QUERY_API = "?api_key="

    private const val URL_POSTER_PATH = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    private const val URL_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

    private const val RESPONSE_TIMEOUT = 10_000

    private val executor: Executor = Executors.newCachedThreadPool()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun getMovies(
        adult: Boolean,
        callback: (result: RepositoryResult<ArrayList<MovieGroup>>) -> Unit
    ) {
        executor.execute {
            var url: URL
            var connection: HttpsURLConnection? = null
            val gson = Gson()
            val movieGroups: ArrayList<MovieGroup> = ArrayList()

            try {
                url = URL(URL_POPULAR)
                connection = url.openConnection() as HttpsURLConnection

                with(connection) {
                    requestMethod = REQUEST_METHOD_GET
                    readTimeout = RESPONSE_TIMEOUT
                    fun response(): ResponseMovieList = gson.fromJson(
                        inputStream.bufferedReader(),
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        POPULAR_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }

                url = URL(URL_NOW_PLAYING)
                connection = url.openConnection() as HttpsURLConnection

                with(connection) {
                    requestMethod = REQUEST_METHOD_GET
                    readTimeout = RESPONSE_TIMEOUT
                    fun response(): ResponseMovieList = gson.fromJson(
                        inputStream.bufferedReader(),
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        NOW_PLAYING_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }

                url = URL(URL_UPCOMING)
                connection = url.openConnection() as HttpsURLConnection

                with(connection) {
                    requestMethod = REQUEST_METHOD_GET
                    readTimeout = RESPONSE_TIMEOUT
                    fun response(): ResponseMovieList = gson.fromJson(
                        inputStream.bufferedReader(),
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        UPCOMING_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }

                url = URL(URL_TOP_RATED)
                connection = url.openConnection() as HttpsURLConnection

                with(connection) {
                    requestMethod = REQUEST_METHOD_GET
                    readTimeout = RESPONSE_TIMEOUT
                    fun response(): ResponseMovieList = gson.fromJson(
                        inputStream.bufferedReader(),
                        ResponseMovieList::class.java
                    )
                    movieGroups.addMovies(
                        response(),
                        TOP_RATED_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }

                mainThreadHandler.post {
                    callback.invoke(Success(movieGroups))
                }
            } catch (exc: Exception) {
                mainThreadHandler.post {
                    callback.invoke(Error(exc))
                }
            } finally {
                connection?.disconnect()
            }
        }
    }

    //Метод получения подробной информации о фильме
    override fun getMovieDetail(
        movieID: Int,
        movie: Movie,
        callback: (result: RepositoryResult<Movie>) -> Unit
    ) {
        executor.execute {
            var connection: HttpsURLConnection? = null
            val gson = Gson()

            try {
                val url =
                    URL("$URL_MOVIE_DETAIL${movie.id}$QUERY_API${BuildConfig.API_KEY}$QUERY_LNG")
                connection = url.openConnection() as HttpsURLConnection

                with(connection) {
                    requestMethod = REQUEST_METHOD_GET
                    readTimeout = RESPONSE_TIMEOUT

                    fun response(): ResponseMovieDetail = gson.fromJson(
                        inputStream.bufferedReader(),
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
            } catch (exc: Exception) {
                mainThreadHandler.post {
                    callback.invoke(Error(exc))
                }
            } finally {
                connection?.disconnect()
            }
        }
    }
}