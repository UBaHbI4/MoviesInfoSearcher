package softing.ubah4ukdev.moviesinfosearcher.domain.storage

import android.content.Context
import softing.ubah4ukdev.moviesinfosearcher.R

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.storage

Created by Ivan Sheynmaer

2021.06.09
v1.0
 */
class MovieStorage(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.shared_preference_name),
        Context.MODE_PRIVATE
    )

    var adultAccessed: Boolean
        get() = sharedPreferences.getBoolean(context.getString(R.string.key_adult_accessed), false)
        set(value) {
            sharedPreferences
                .edit()
                .putBoolean(context.getString(R.string.key_adult_accessed), value)
                .apply()
        }
}