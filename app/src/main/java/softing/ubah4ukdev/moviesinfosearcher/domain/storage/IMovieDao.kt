package softing.ubah4ukdev.moviesinfosearcher.domain.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.storage

Created by Ivan Sheynmaer

2021.06.15
v1.0
 */
@Dao
interface IMovieDao {

    @Query("SELECT * FROM HistoryTable")
    suspend fun all(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: MovieEntity)
}