package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

import softing.ubah4ukdev.moviesinfosearcher.domain.Movie

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
interface IMovieLongClickable {
    fun onMovieLongClick(position: Int, movie: Movie)
}