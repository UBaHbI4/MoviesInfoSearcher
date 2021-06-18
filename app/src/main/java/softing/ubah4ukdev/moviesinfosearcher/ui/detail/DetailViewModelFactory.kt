package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.ILocalRepository
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.IMovieRepository

class DetailViewModelFactory(
    private val resourceProvider: ResourceProvider,
    private val repository: IMovieRepository,
    private val localRepository: ILocalRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(resourceProvider, repository, localRepository) as T
}