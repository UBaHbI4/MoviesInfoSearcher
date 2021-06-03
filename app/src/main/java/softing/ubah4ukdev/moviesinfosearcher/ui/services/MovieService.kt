package softing.ubah4ukdev.moviesinfosearcher.ui.services

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.MoviesRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.RepositoryResult
import softing.ubah4ukdev.moviesinfosearcher.domain.Success
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.services

Created by Ivan Sheynmaer

2021.06.05
v1.0
 */
class MovieService() : IntentService("MovieService") {
    private val repository: MoviesRepositoryImpl = MoviesRepositoryImpl

    companion object {
        const val ACTION_RESULT = "MovieService.ACTION_RESULT"
        const val ARG_DETAIL = "ARG_DETAIL"
    }

    override fun onHandleIntent(intent: Intent?) {
        val movie = intent?.getParcelableExtra<Movie>(HomeFragment.MOVIE_ARG)

        movie?.let {
            val result: RepositoryResult<Movie> = repository.getMovieDetail(movie.id, movie)

            (result as? Success<Movie>)?.let {
                Intent(ACTION_RESULT).apply {
                    putExtra(ARG_DETAIL, it.value)
                }.also {
                    LocalBroadcastManager.getInstance(this)
                        .sendBroadcast(it)
                }
            }
        }

    }
}