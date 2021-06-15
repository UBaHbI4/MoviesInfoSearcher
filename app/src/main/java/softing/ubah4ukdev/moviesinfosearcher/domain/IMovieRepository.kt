package softing.ubah4ukdev.moviesinfosearcher.domain

import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieEntity

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
interface IMovieRepository {
    //Получение списка категорий со списками фильмов
    fun getMovies(
        adult: Boolean,
        callback: (result: RepositoryResult<ArrayList<MovieGroup>>) -> Unit
    )

    //Метод получения подробной информации о фильме.
    fun getMovieDetail(
        movieID: Int,
        movie: Movie,
        callback: (result: RepositoryResult<Movie>) -> Unit
    )

    //Метод получения истории просмотров детализации о фильмах
    suspend fun getHistory(
    ): List<MovieEntity>

    //Метод добавления данных о просмотренном фильме в БД
    suspend fun addToHistory(entity: MovieEntity)
}