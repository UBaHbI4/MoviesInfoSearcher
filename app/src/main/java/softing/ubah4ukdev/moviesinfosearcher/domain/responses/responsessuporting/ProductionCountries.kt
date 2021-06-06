package softing.ubah4ukdev.moviesinfosearcher.domain.responses.responsessuporting

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.responses

Created by Ivan Sheynmaer

2021.06.03
v1.0
 */
@Parcelize
data class ProductionCountries(
    @SerializedName("iso_3166_1")
    val iso3166One: String,
    @SerializedName("name")
    val name: String
) : Parcelable