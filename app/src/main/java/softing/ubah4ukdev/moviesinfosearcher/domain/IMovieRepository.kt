package softing.ubah4ukdev.moviesinfosearcher.domain

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
interface IMovieRepository {
    //Получение списка фильмов, которые проигрываются сейчас
    fun getMoviesNowPlaying(callback: ICallback<ArrayList<Movie>>)

    //Получение списка фильмов, которые ожидаются в прокат
    fun getMoviesUpComing(callback: ICallback<ArrayList<Movie>>)
}