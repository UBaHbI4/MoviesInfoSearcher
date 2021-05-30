package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider

class HomeViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(resourceProvider) as T
}