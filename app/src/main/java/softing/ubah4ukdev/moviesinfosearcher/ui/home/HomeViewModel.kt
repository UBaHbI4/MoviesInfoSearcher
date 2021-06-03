package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.IMovieRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.Success

//resourceProvider (для доступа к строковым ресурсам) теперь завязан на жизненный цикл фрагмента.
class HomeViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository
) : ViewModel(), LifecycleObserver {

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _moviesLiveData = MutableLiveData<ArrayList<MovieGroup>>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val movieLiveData: LiveData<ArrayList<MovieGroup>> = _moviesLiveData

    fun getMovies() {
        _loadingLiveData.value = true

        repository.getMovies {
            when (it) {
                is Success -> {
                    _moviesLiveData.value = it.value ?: arrayListOf()
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