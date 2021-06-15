package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieStorage

class SettingsViewModel(
    private val resourceProvider: ResourceProvider,
    private val movieStorage: MovieStorage
) : ViewModel() {

    private val _adult = MutableStateFlow<Boolean>(false)
    val adult: Flow<Boolean> = _adult

    init {
        viewModelScope.launch {
            _adult.value = movieStorage.adultAccessed
        }
    }

    fun onAdultEnableChanged(isChecked: Boolean) {
        movieStorage.adultAccessed = isChecked
    }
}