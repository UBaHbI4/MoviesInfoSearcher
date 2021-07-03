package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location

Created by Ivan Sheynmaer

2021.07.03
v1.0
 */
interface ILocationRepository {

    fun address(latitude: Double, longitude: Double): String?
}