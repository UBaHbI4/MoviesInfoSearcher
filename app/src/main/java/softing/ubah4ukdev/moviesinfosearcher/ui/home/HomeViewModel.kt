package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.*

//resourceProvider (для доступа к строковым ресурсам) теперь завязан на жизненный цикл фрагмента.
class HomeViewModel(private val resourceProvider: ResourceProvider) : ViewModel(),
    LifecycleObserver {
    private val repository: IMovieRepository = MockMoviesRepositoryImpl
    private val _titleMovie = MutableLiveData<String>()
    val titleMovie: LiveData<String> = _titleMovie

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _moviewPlayNowLiveData = MutableLiveData<ArrayList<Movie>?>()
    private val _moviewUpComingLiveData = MutableLiveData<ArrayList<Movie>?>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val moviewPlayNowLiveData: LiveData<ArrayList<Movie>?> = _moviewPlayNowLiveData
    val moviewUpComingLiveData: LiveData<ArrayList<Movie>?> = _moviewUpComingLiveData

    fun getFilms() {
        _loadingLiveData.value = true

        repository.getMoviesNowPlaying {

            when (it) {
                is Success -> {
                    _moviewPlayNowLiveData.value = it.value
                    _errorLiveData.value = null
                }
                is Error -> {
                    _errorLiveData.value = it.value.message.toString()
                }
            }
        }

        repository.getMoviesUpComing {
            when (it) {
                is Success -> {
                    _moviewUpComingLiveData.value = it.value
                    _errorLiveData.value = null
                }
                is Error -> {
                    _errorLiveData.value = it.value.message.toString()
                }
            }
            _loadingLiveData.value = false
        }
    }

    //Клик по фильму
    fun onMovieClick(position: Int, movie: Movie) {
        _titleMovie.value =
            "${movie.title}\r\n${resourceProvider.getString(R.string.position)}: $position\r\n\r\n${movie.overview}"
    }
}