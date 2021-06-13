package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.IMovieRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieStorage

class HomeViewModelFactory(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
    private val movieStorage: MovieStorage
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(resourceProvider, repository, movieStorage) as T
}