package softing.ubah4ukdev.moviesinfosearcher.ui.map

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location.ILocationRepository

class MapsViewModel(
    private val repository: ILocationRepository
) : ViewModel(), LifecycleObserver {

    fun address(latitude: Double, longitude: Double): String? {
        return repository.address(latitude, longitude)
    }
}