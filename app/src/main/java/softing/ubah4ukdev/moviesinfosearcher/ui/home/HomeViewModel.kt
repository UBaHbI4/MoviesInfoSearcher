package softing.ubah4ukdev.moviesinfosearcher.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import softing.ubah4ukdev.moviesinfosearcher.domain.*

class HomeViewModel(private val liveDataToObserver :MutableLiveData<AppState> = MutableLiveData()) : ViewModel(), LifecycleObserver {
    private val repository:IMovieRepository = MockMoviesRepositoryImpl

    private val _titleMovie = MutableLiveData<String>()
    val titleMovie: LiveData<String> = _titleMovie

    fun getLiveData() = liveDataToObserver

    fun getFilmsPlayingNow() {
        liveDataToObserver.value = AppState.Loading
        repository.getMoviesNowPlaying(object : ICallback<ArrayList<Movie>> {
            override fun onResult(value: ArrayList<Movie>) {
                /*
                Будем получать результат в случайном порядке, либо все ОК и вернем списки фильмов,
                либо ошибка, покажем текст ошибки
                */
                val rnd = (0..1).random()
                if(rnd == 0) {
                    val moviesPlayNow: ArrayList<Movie> = value.filter { it.category == 1 } as ArrayList<Movie>
                    val moviesUpComing: ArrayList<Movie> = value.filter { it.category == 2 } as ArrayList<Movie>
                    liveDataToObserver.value = AppState.Success(moviesPlayNow, moviesUpComing)
                } else {
                    liveDataToObserver.value = AppState.Error(Exception("Ошибка подключения. Проверьте интернет"))
                }
            }
        })
    }

    //Клик по фильму
    fun onMovieClick(position: Int, movie: Movie) {
        _titleMovie.value = "${movie.title!!} позиция: $position \r\n${movie.overview}"
    }
}