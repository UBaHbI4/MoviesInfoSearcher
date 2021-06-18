package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.RepositoryResult
import softing.ubah4ukdev.moviesinfosearcher.domain.Success
import softing.ubah4ukdev.moviesinfosearcher.domain.extensions.addMovies
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.network.AddHeaderInterceptor
import softing.ubah4ukdev.moviesinfosearcher.domain.network.ITheMovieDbApi
import softing.ubah4ukdev.moviesinfosearcher.domain.network.responses.ResponseMovieDetail
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
//Репозиторий, с загрузкой данных через Retrofit
object MoviesRetrofitRepositoryImpl : IMovieRepository {

    private const val URL_BASE = "https://api.themoviedb.org/3/movie/"

    private const val UPCOMING_TITLE = "Up Coming"
    private const val TOP_RATED_TITLE = "Top rated"
    private const val POPULAR_TITLE = "Popular"
    private const val NOW_PLAYING_TITLE = "Now playing"

    private const val URL_POSTER_PATH = "https://image.tmdb.org/t/p/w154"
    private const val URL_BACKDROP_PATH = "https://image.tmdb.org/t/p/w300"

    private val executor: Executor = Executors.newCachedThreadPool()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    /*
    *  В данном методе попробуем вызов через execute()
    */
    override fun getMovies(
        adult: Boolean,
        callback: (result: RepositoryResult<ArrayList<MovieGroup>>) -> Unit
    ) {
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

            val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            val ret: ITheMovieDbApi = retrofit.create(ITheMovieDbApi::class.java)

            var response = ret.getMoviesUpcoming(adult)
                .execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    movieGroups.addMovies(
                        it,
                        UPCOMING_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }
            response = ret.getMoviesTopRated(adult)
                .execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    movieGroups.addMovies(
                        it,
                        TOP_RATED_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            response = ret.getMoviesPopular(adult)
                .execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    movieGroups.addMovies(
                        it,
                        POPULAR_TITLE,
                        URL_POSTER_PATH,
                        URL_BACKDROP_PATH
                    )
                }
            }

            response = ret.getMoviesNowPlaying(adult)
                .execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    movieGroups.addMovies(
                        it,
                        NOW_PLAYING_TITLE,
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
    *  В данном методе попробуем вызов через enqueue, с callback-ом, ассинхронный вызов
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

        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofit.create(ITheMovieDbApi::class.java).getMoviesDetail(movieID)
            .enqueue(
                object : retrofit2.Callback<ResponseMovieDetail> {
                    override fun onResponse(
                        call: Call<ResponseMovieDetail>,
                        response: Response<ResponseMovieDetail>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val movieDetail: Movie = it.let {
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
                                callback.invoke(Success(movieDetail))
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseMovieDetail>, t: Throwable) {
                        callback.invoke(Error(Exception("")))
                    }
                }
            )
    }
}