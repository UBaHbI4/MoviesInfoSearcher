package softing.ubah4ukdev.moviesinfosearcher.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location.ILocationRepository

class MapsViewModelFactory(
    private val repository: ILocationRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MapsViewModel(repository) as T
}