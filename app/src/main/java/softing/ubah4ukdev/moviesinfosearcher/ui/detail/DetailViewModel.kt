package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.IMovieRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.Success

class DetailViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository
) : ViewModel() {

    private val _localLoadingLiveData = MutableLiveData(false)
    private val _localErrorLiveData = MutableLiveData<String?>()
    private val _localMovieLiveData = MutableLiveData<Movie>()

    val localLoadingLiveData: LiveData<Boolean> = _localLoadingLiveData
    val localErrorLiveData: LiveData<String?> = _localErrorLiveData
    val localMovieLiveData: LiveData<Movie> = _localMovieLiveData

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _movieLiveData = MutableLiveData<Movie>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val movieLiveData: LiveData<Movie> = _movieLiveData

    fun loadMovieLocal(currentMovie: Movie) {
        _localLoadingLiveData.value = true
        if (currentMovie != null) {
            _localMovieLiveData.value = currentMovie
            _localErrorLiveData.value = null
            _localLoadingLiveData.value = false
        } else {
            _localErrorLiveData.value = resourceProvider.getString(R.string.movie_error_loading)
            _localLoadingLiveData.value = false
        }
    }

    fun loadMovie(currentMovie: Movie) {
        _loadingLiveData.value = true
        if (currentMovie != null) {
            _movieLiveData.value = currentMovie
            _errorLiveData.value = null
            _loadingLiveData.value = false
        } else {
            _errorLiveData.value = resourceProvider.getString(R.string.movie_error_loading)
            _loadingLiveData.value = false
        }
    }

    fun movieDetail(currentMovie: Movie) {
        _loadingLiveData.value = true
        repository.getMovieDetail(currentMovie.id, currentMovie) {
            when (it) {
                is Success -> {
                    it.value.let {
                        _movieLiveData.value =it
                    }
                    _errorLiveData.value = null
                    _loadingLiveData.value = false
                }
                is Error -> {
                    _errorLiveData.value = it.value.message.toString()
                    _loadingLiveData.value = false
                }
            }
        }

    }
}