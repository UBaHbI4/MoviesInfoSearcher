package softing.ubah4ukdev.moviesinfosearcher.app

import android.app.Application
import androidx.room.Room
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.IMovieDao
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieDatabase

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.app

Created by Ivan Sheynmaer

2021.06.15
v1.0
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: MovieDatabase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): IMovieDao {
            if (db == null) {
                synchronized(MovieDatabase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MovieDatabase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.movieDao()
        }
    }
}

