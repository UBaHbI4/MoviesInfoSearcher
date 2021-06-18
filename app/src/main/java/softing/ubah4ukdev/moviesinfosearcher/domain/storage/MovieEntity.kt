package softing.ubah4ukdev.moviesinfosearcher.domain.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.storage

Created by Ivan Sheynmaer

2021.06.15
v1.0
 */
@Entity(tableName = "HistoryTable")
data class MovieEntity(
    @PrimaryKey (autoGenerate = true)
    val histID : Int,
    val date :Long,
    val adult: Boolean,
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
    //Метод временный, нужен для тестов, далее удалю
    override fun toString(): String {
        val dateString =  SimpleDateFormat("dd.MM.yy hh:mm:ss").format(Date(this.date))
        return "\r\nhistID=${this.histID}  ${this.title}  ${dateString}\r\n"
    }
}