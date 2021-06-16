package softing.ubah4ukdev.moviesinfosearcher.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.ILocalRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.IMovieRepository

class HistoryViewModelFactory(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
    private val localRepository: ILocalRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HistoryViewModel(resourceProvider, repository, localRepository) as T
}