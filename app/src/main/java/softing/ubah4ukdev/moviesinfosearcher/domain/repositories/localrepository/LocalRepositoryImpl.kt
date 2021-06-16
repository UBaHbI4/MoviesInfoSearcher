package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository

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

    override suspend fun getHistory(): List<MovieEntity> {
        val temp: List<MovieEntity> = dao.all()
        return temp
    }

    override suspend fun addToHistory(entity: MovieEntity) {
        dao.add(entity)
    }
}