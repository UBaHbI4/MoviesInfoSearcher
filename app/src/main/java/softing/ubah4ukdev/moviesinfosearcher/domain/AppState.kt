package softing.ubah4ukdev.moviesinfosearcher.domain

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.05
v1.0
 */
sealed class AppState {
    data class Success(val moviesPlayNow: ArrayList<Movie>, val moviesUpComing: ArrayList<Movie>) :
        AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
