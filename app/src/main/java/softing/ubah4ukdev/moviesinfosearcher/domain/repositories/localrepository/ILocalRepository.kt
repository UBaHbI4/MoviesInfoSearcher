package softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository

import kotlinx.coroutines.flow.Flow
import softing.ubah4ukdev.moviesinfosearcher.domain.RepositoryResult
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieEntity

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.06.16
v1.0
 */
interface ILocalRepository {

    //Метод получения истории просмотров детализации о фильмах
    suspend fun getHistory(
    ): Flow<RepositoryResult<List<MovieEntity>>>

    //Метод добавления данных о просмотренном фильме в БД
    suspend fun addToHistory(entity: MovieEntity)

    //Метод для очистки истории просмотров
    suspend fun removeAll():Flow<Boolean?>
}