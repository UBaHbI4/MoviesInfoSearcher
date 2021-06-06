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
data class ProductionCompanies(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
) : Parcelable
