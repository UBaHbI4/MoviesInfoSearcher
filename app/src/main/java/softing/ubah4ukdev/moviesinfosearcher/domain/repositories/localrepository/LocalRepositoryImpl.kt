package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softing.ubah4ukdev.moviesinfosearcher.domain.Error
import softing.ubah4ukdev.moviesinfosearcher.domain.RepositoryResult
import softing.ubah4ukdev.moviesinfosearcher.domain.Success
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.IMovieDao
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieEntity

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.06.16
v1.0
 */
open class LocalRepositoryImpl(
    private val dao: IMovieDao
) : ILocalRepository {

    companion object {
        private var INSTANCE: LocalRepositoryImpl? = null
        fun getInstance(app: IMovieDao): LocalRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = LocalRepositoryImpl(
                    dao = app
                )
            }
            return INSTANCE as LocalRepositoryImpl
        }
    }

    override suspend fun getHistory(): Flow<RepositoryResult<List<MovieEntity>>> = flow {
        val historyMovies = dao.all()
        historyMovies?.let {
            emit(Success(historyMovies))
        }
    }

    override suspend fun addToHistory(entity: MovieEntity) {
        dao.add(entity)
    }

    override suspend fun removeAll(): Flow<Boolean> = flow {
        Thread.sleep(500)
        val recordCount: Int = dao.recordCount()
        Log.d("movieDebug", recordCount.toString())
        if (recordCount > 0) {
            dao.removeAll()
            emit(true)
        } else {
            emit(false)
        }
    }
}