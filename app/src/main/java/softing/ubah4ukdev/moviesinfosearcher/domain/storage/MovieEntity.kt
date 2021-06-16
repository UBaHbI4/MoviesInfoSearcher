package softing.ubah4ukdev.moviesinfosearcher.domain.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.storage

Created by Ivan Sheynmaer

2021.06.15
v1.0
 */
@Entity(tableName = "HistoryTable")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val revenue: Int = 0,
    val runtime: Int = 0,
    var comment: String = ""
) {
    override fun toString(): String {
        return "\r\n${this.title}\r\n"
    }
}