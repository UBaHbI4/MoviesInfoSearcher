package softing.ubah4ukdev.moviesinfosearcher.ui.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.ILocalRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.IMovieRepository

class HistoryViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
    private val localRepository: ILocalRepository
) : ViewModel() {

    fun getHistory() {
        viewModelScope.launch {
            val temp = localRepository.getHistory()
            Log.d("movieDebug", temp.toString())
        }
    }
}