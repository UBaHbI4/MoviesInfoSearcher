package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location

import android.content.Context
import android.location.Geocoder

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location

Created by Ivan Sheynmaer

2021.07.03
v1.0
 */
class LocationRepositoryImpl(val context: Context) : ILocationRepository {
    private val RESULT_COUNT_GEOCODER = 1

    override fun address(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context)
        return geocoder.getFromLocation(latitude, longitude, RESULT_COUNT_GEOCODER).firstOrNull()
            ?.getAddressLine(0)
    }
}