package softing.ubah4ukdev.moviesinfosearcher.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.Success
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieHistory
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.ILocalRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.IMovieRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieEntity

class HistoryViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
    private val localRepository: ILocalRepository
) : ViewModel() {


    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _movies = MutableStateFlow<List<MovieHistory>?>(listOf())
    private val _deleted = MutableSharedFlow<Boolean?>()

    val loading: StateFlow<Boolean> = _loading
    val error: StateFlow<String?> = _error
    val movies: StateFlow<List<MovieHistory>?> = _movies
    val deleted: Flow<Boolean?> = _deleted

    private fun listMoveEntityToListArrayMove(target: List<MovieEntity>): ArrayList<MovieHistory> {
        var result: ArrayList<MovieHistory> = arrayListOf()
        target.forEach {
            result.add(
                MovieHistory(
                    it.histID,
                    it.date,
                    it.adult,
                    it.id,
                    it.overview,
                    it.popularity,
                    it.posterPath,
                    it.title,
                    it.voteAverage,
                    0
                )
            )
        }
        return result
    }

    fun getHistory() {
        viewModelScope.launch {
            localRepository.getHistory()
                .flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                }
                .collect { result ->
                    _loading.value = false
                    when (result) {
                        is Success -> {
                            _movies.value = listMoveEntityToListArrayMove(result.value)
                            _error.value = null
                        }
                        is Error -> {
                            _error.value = result.value.message
                        }
                    }
                }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            localRepository.removeAll()
                .flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                }
                .collect {
                    it?.let {
                        if (it) {
                            _error.value = null
                            _loading.value = false
                            _deleted.emit(true)
                        } else {
                            _error.value = null
                            _loading.value = false
                            _deleted.emit(false)
                        }
                    }
                }
        }
    }
}