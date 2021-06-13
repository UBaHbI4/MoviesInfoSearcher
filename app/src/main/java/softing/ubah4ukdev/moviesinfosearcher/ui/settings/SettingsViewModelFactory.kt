package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieStorage

class SettingsViewModelFactory(
    private val resourceProvider: ResourceProvider,
    private val movieStorage: MovieStorage
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SettingsViewModel(resourceProvider, movieStorage) as T
}