package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.domain.ICallback
import softing.ubah4ukdev.moviesinfosearcher.domain.MockMoviesRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<ArrayList<Movie>>()
    val movies: LiveData<ArrayList<Movie>> = _movies

    private val _moviesUpComing = MutableLiveData<ArrayList<Movie>>()
    val moviesUpComing: LiveData<ArrayList<Movie>> = _moviesUpComing

    private val _titleMovie = MutableLiveData<String>()
    val titleMovie: LiveData<String> = _titleMovie

    private val _isLoading = MutableLiveData<Int>()
    val isLoading: LiveData<Int> = _isLoading


    fun getFilmsPlayingNow() {
        _isLoading.value = View.VISIBLE
        MockMoviesRepositoryImpl.getMoviesNowPlaying(object : ICallback<ArrayList<Movie>> {
            override fun onResult(value: ArrayList<Movie>) {
                _movies.value = value
            }
        })
    }

    fun getFilmsUpcoming() {
        MockMoviesRepositoryImpl.getMoviesUpComing(object : ICallback<ArrayList<Movie>> {
            override fun onResult(value: ArrayList<Movie>) {
                _moviesUpComing.value = value
                _isLoading.value = View.GONE
            }
        })
    }

    //Клик по фильму
    fun onMovieClick(position: Int, movie: Movie) {
        _titleMovie.value = "${movie.title!!} позиция: $position \r\n${movie.overview}"
    }
}