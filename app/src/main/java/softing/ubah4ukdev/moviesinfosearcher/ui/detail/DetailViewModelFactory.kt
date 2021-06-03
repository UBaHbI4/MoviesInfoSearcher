package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.IMovieRepository

class DetailViewModelFactory(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(resourceProvider, repository) as T
}