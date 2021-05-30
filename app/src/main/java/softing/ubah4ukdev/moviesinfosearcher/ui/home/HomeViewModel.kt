package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.*

//resourceProvider (для доступа к строковым ресурсам) теперь завязан на жизненный цикл фрагмента.
class HomeViewModel(private val resourceProvider: ResourceProvider) : ViewModel(),
    LifecycleObserver {
    private val repository: IMovieRepository = MockMoviesRepositoryImpl

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _moviesLiveData = MutableLiveData<ArrayList<MovieGroup>?>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val movieLiveData: LiveData<ArrayList<MovieGroup>?> = _moviesLiveData

    fun getFilms() {
        _loadingLiveData.value = true

        repository.getMovies {
            when (it) {
                is Success -> {
                    _moviesLiveData.value = it.value
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