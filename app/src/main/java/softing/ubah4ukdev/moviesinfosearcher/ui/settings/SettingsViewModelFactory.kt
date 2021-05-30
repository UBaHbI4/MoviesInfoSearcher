package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider

class SettingsViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SettingsViewModel(resourceProvider) as T
}