package softing.ubah4ukdev.moviesinfosearcher.domain.storage;

import androidx.room.Database
import androidx.room.RoomDatabase

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain.storage

Created by Ivan Sheynmaer

2021.06.15
v1.0
 */
@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): IMovieDao
}
