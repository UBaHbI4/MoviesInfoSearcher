package softing.ubah4ukdev.moviesinfosearcher

import android.app.Application

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher

Created by Ivan Sheynmaer

2021.05.19
v1.0
 */
class ResourceProvider(private val app: Application) {
    fun getString(resID: Int) = app.getString(resID)
}