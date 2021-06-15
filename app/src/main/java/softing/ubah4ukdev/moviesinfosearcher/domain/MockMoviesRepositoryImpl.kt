package softing.ubah4ukdev.moviesinfosearcher.domain

import android.os.Handler
import android.os.Looper
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieEntity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
//Временная реализация репозитория с заданными списками фильмов заранее
object MockMoviesRepositoryImpl : IMovieRepository {
    private const val DELAY_RESPONSE = 1500L

    private val executor: Executor = Executors.newCachedThreadPool()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    private val moviesGroup = arrayListOf(
        MovieGroup(
            "Популярные", arrayListOf(
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                )
            )
        ),
        MovieGroup(
            "Скоро в прокате", arrayListOf(
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
            )
        ),
        MovieGroup(
            "Детское", arrayListOf(
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
                Movie(
                    false,
                    "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                    arrayListOf(),
                    0,
                    "En",
                    "MORTAL KOMBAT",
                    "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?",
                    128.6,
                    "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                    "2021-05-06",
                    "Мортал Комбат",
                    false,
                    45.5,
                    45
                ),
            )
        )
    )

    override fun getMovies(
        adult: Boolean,
        callback: (result: RepositoryResult<ArrayList<MovieGroup>>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(DELAY_RESPONSE)
            val result: ArrayList<MovieGroup> = moviesGroup
            mainThreadHandler.post {
                callback(
                    Success(result)
                )
            }
        }
    }

    override fun getMovieDetail(
        movieID: Int,
        movie: Movie,
        callback: (result: RepositoryResult<Movie>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(DELAY_RESPONSE)
            mainThreadHandler.post {
                callback(
                    Success(movie)
                )
            }
        }
    }

    override suspend fun getHistory(): List<MovieEntity> {
        return listOf()
    }

    override suspend fun addToHistory(entity: MovieEntity) {
    }
}