package softing.ubah4ukdev.moviesinfosearcher.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.IMovieRepository

class HistoryViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
) : ViewModel() {

    fun getHistory() {
        viewModelScope.launch {
            repository.getHistory()
        }
    }
}