package softing.ubah4ukdev.moviesinfosearcher.domain

import android.os.Handler
import android.os.Looper
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
    private val executor: Executor = Executors.newCachedThreadPool()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    private var moviesPlayingNow: ArrayList<Movie> = ArrayList<Movie>()
    private var moviesUpcoming: ArrayList<Movie> = ArrayList<Movie>()

    init {
        moviesPlayingNow = arrayListOf(
                Movie(
                        460465,
                        "Мортал Комбат",
                        "2021-04-07",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/b1jxegUWWwAwfGTV780wxwtwmQF.jpg",
                        "https://image.tmdb.org/t/p/w780/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg",
                        "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии и почему император Внешнего Мира Шан Цзун посылает своего лучшего воина, могущественного криомансера Саб-Зиро, на охоту за Коулом. Янг боится за безопасность своей семьи, и майор спецназа Джакс, обладатель такой же отметки в виде дракона, как и у Коула, советует ему отправиться на поиски Сони Блейд. Вскоре он оказывается в храме Лорда Рейдена, Старшего Бога и защитника Земного Царства, который дает убежище тем, кто носит метку. Здесь Коул тренируется с опытными воинами Лю Каном, Кун Лао и наемником-изгоем Кано, готовясь вместе с величайшими чемпионами Земли противостоять врагам из Внешнего Мира в битве за вселенную. Но сможет ли Коул приложить все усилия и раскрыть в себе аркану — могущественную силу его души, чтобы не только спасти свою семью, но и остановить Внешний Мир раз и навсегда?"
                ),
                Movie(
                        399566,
                        "Годзилла против Конга\"",
                        "2021-03-24",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/w6s3yQIMKMc6cmtdEtBkkGCA3V1.jpg",
                        "https://image.tmdb.org/t/p/w780/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg",
                        "Конг и группа ученых отправляются в опасное путешествие в поисках родного дома гиганта. Среди них девочка Джия, единственная, кто умеет общаться с Конгом. Неожиданно они сталкиваются с разъяренным Годзиллой, разрушающим все на своем пути. Битва двух титанов, спровоцированная неведомыми силами — лишь малая часть тайны, спрятанной в недрах Земли."
                ),
                Movie(
                        804435,
                        "Ангел мести",
                        "2021-04-16",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/7RAgd1wPhLIXt9WlOaCxLrzf7k1.jpg",
                        "https://image.tmdb.org/t/p/w780/mb3fcmQzXd8aUf7r6izZfMHSJmz.jpg",
                        "После выхода на пенсию бывший комиссар полиции понял, что прикрывать продажных детективов было ошибкой. Но чтобы подчистить за собой город, он выбирает не самый гуманный способ. Похитив невинного ребенка, он шантажирует его мать. В попытке спасти дочь женщина становится орудием в войне против разбушевавшегося криминала. Впрочем, теперь ее гнева хватит не только на преступников."
                ),
                Movie(
                        635302,
                        "Истребитель демонов: Поезд «Бесконечный»",
                        "2020-10-16",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/sYoduof7H9nI1f8nBTdR2j7DCyt.jpg",
                        "https://image.tmdb.org/t/p/w780/3FVe3OAdgz060JaxIAaUl5lo6cx.jpg",
                        "Тандзиро с друзьями из отряда уничтожителей демонов расследует серию загадочных исчезновений внутри мчащегося поезда. Но компания ещё не знает, что в составе находится один из двенадцати Лунных демонов, и он приготовил для них ловушку."
                ),
                Movie(
                        615457,
                        "Никто",
                        "2021-03-26",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/h3zs8tu6OG37xqo8QlcgwgjLE0y.jpg",
                        "https://image.tmdb.org/t/p/w780/6zbKgwgaaCyyBXE4Sun4oWQfQmi.jpg",
                        "Непримечательный и незаметный человек живёт обычной жизнью, пока однажды, спасая женщину от нападения бандитов, не отправляет одного из хулиганов в больницу. Лишь позже он узнаёт, что это был брат влиятельного гангстера, который теперь жаждет мести."
                )
        )
        moviesUpcoming = arrayListOf(
                Movie(
                        385128,
                        "Форсаж 9",
                        "2021-05-19",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/tx5vLuGQqUhjgxJrUh513HaUur2.jpg",
                        "https://image.tmdb.org/t/p/w780/xXHZeb1yhJvnSHPzZDqee0zfMb6.jpg",
                        "Времена меняются: Доминик Торетто залегает на дно и живет спокойной жизнью с семьей. Но судьба имеет на него другие планы."
                ),
                Movie(
                        520763,
                        "Тихое место 2",
                        "2021-05-27",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2//r9DjNUi9y45satAsgvSaioAXz64.jpg",
                        "https://image.tmdb.org/t/p/w780//z2UtGA1WggESspi6KOXeo66lvLx.jpg",
                        "Семья Эбботт продолжает бороться за жизнь в полной тишине. Вслед за смертельной угрозой, с которой они столкнулись в собственном доме, им предстоит познать ужасы внешнего мира. Они вынуждены отправиться в неизвестность, где быстро обнаруживают, что существа, охотящиеся на звук, — не единственные враги за пределами безопасной песчаной тропы."
                ),
                Movie(
                        600354,
                        "Отец",
                        "2020-12-23",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/l9iHbA887CAzmxK3NL5U1nHPndP.jpg",
                        "https://image.tmdb.org/t/p/w780/h3weAFgg06GqchI2xDfufBgSFTj.jpg",
                        "Энтони далеко не молод и живет один в Лондоне, что очень тревожит его дочь. Она не хочет оставлять отца без присмотра и пытается найти ему сиделку. Энтони отметает все предложенные варианты. Однако, планируя переезд в Париж, дочь не спешит сдаваться. Она становится все настойчивее в попытках найти для упрямого папочки идеальную женщину."
                ),
                Movie(
                        486589,
                        "Красные туфельки и семь гномов",
                        "2019-07-25",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/nN4Gs3vZAOJ1D6FRtrwbU9VGYwU.jpg",
                        "https://image.tmdb.org/t/p/w780/7porHcTZYYOC7433249nrmCdti6.jpg",
                        "Анимационный фильм рассказывает о семи самовлюбленных принцах-красавцах, на которых было наложено заклятие, превратившее их в семерых уродливых гномов. Чтобы снять проклятье, они должны разыскать пару магических красных туфелек, которые носит одна девушка. Туфли помогают девушке выглядеть стройнее, чем она есть на самом деле."
                ),
                Movie(
                        337404,
                        "Круэлла",
                        "2021-05-26",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/nuBzkLid7XWa4feBZv75c7bHJ43.jpg",
                        "https://image.tmdb.org/t/p/w780/nSrc9olOYrUUTmSRsQIHRBXEPV1.jpg",
                        "История о коварной злодейке Круэлле де Вил, которая обожает всё чёрно-белое и мечтает сделать шубу из шкурок далматинцев."
                ),
                Movie(
                        647302,
                        "Benny Loves You",
                        "2021-05-07",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/mQ8vALvqA0z0qglG3gI6xpVcslo.jpg",
                        "https://image.tmdb.org/t/p/w780/njxvIJjQJZJi4ihkh95pidxOJZY.jpg",
                        ""
                ),
                Movie(
                        741228,
                        "Локдаун",
                        "2021-02-25",
                        "https://image.tmdb.org/t/p/w185_and_h278_bestv2/4qu4kO5HVTKMK2hvmCXeviZ233l.jpg",
                        "https://image.tmdb.org/t/p/w780/svHelD0Hb3TXPAQoPsoBwdDMTvf.jpg",
                        "История о рискованном ограблении, разворачивающемся на фоне пандемии коронавируса. Давние спарринг-партнёры Линда и Пакстон договариваются о временном перемирии, чтобы вместе совершить дерзкую кражу ювелирных изделий из самого известного лондонского универмага — Harrods."
                )
        )
    }

    override fun getMoviesNowPlaying(callback: ICallback<ArrayList<Movie>>) {
        executor.execute {
            Thread.sleep(500)
            val result: ArrayList<Movie> = moviesPlayingNow
            mainThreadHandler.post {
                callback.onResult(result)
            }
        }
    }

    override fun getMoviesUpComing(callback: ICallback<ArrayList<Movie>>) {
        executor.execute {
            Thread.sleep(1500)
            val result: ArrayList<Movie> = moviesUpcoming
            mainThreadHandler.post {
                callback.onResult(result)
            }
        }
    }
}