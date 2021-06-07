package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentDetailBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.MoviesRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.showSnakeBar
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.visible
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewBinding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )

    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            ResourceProvider(requireActivity().application),
            MoviesRepositoryImpl
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMovie: Movie? = requireArguments().getParcelable(HomeFragment.MOVIE_ARG)

        //Клик по иконке для воспроизведения трейлера к фильму
        viewBinding.video.setOnClickListener {
            context?.let {
                val msg: String = it.getString(R.string.movie_trailer_message)
                view.showSnakeBar(msg, Snackbar.LENGTH_SHORT)
            }
        }

        //Клик по иконке для перехода на домашнюю страницу фильма
        viewBinding.webSite.setOnClickListener {
            context?.let {
                val msg: String = it.getString(R.string.movie_home_page_message)
                view.showSnakeBar(msg, Snackbar.LENGTH_SHORT)
            }
        }

        //Лайвдаты для отображения стартовых данных о фильме, которые уже загружены в списке фильмов
        // и переданы в детализацию
        detailViewModel.localMovieLiveData.observe(viewLifecycleOwner) {
            with(viewBinding) {
                movieTitle.text = it?.title
                movieOverview.text = it?.overview
                rating.text = it?.voteAverage.toString()
                movieReleaseDate.text = it?.releaseDate

                Glide.with(moviePoster)
                    .load(it?.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.ic_no_image)
                    .into(moviePoster)

                Glide.with(movieBackdrop)
                    .load(it?.backdropPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.ic_no_image)
                    .into(movieBackdrop)
            }
        }
        detailViewModel.localLoadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visible { it }
        }
        detailViewModel.localErrorLiveData.observe(viewLifecycleOwner) {
            val error = it ?: return@observe
            viewBinding.progress.visible { false }

            Snackbar
                .make(
                    viewBinding.root,
                    error,
                    Snackbar.LENGTH_INDEFINITE
                )
                .also {
                    it.view.also {
                        (it.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?)?.maxLines =
                            HomeFragment.MAX_LINES
                    }
                }
                .show()
        }

        //Лайвдаты для отображения подгружаемых более детальных данных для выбранного фильма
        detailViewModel.movieLiveData.observe(viewLifecycleOwner) {
            with(viewBinding) {
                movieBudget.text = it.budget.toString()
                //video.visible { it.video }
                adult.visible { it.adult }
                "${it.originalTitle} (${it.originalLanguage})".also { movieOriginalTitle.text = it }
                moviePopularity.text = it.popularity.toString()
                movieVoteCount.text = it.voteCount.toString()
                movieRevenue.text = it.revenue.toString()
                "${it.runtime}m".also { movieRuntime.text = it }
                movieStatus.text = it.status
                movieTagLine.text = it.tagline.let {
                    if (!it.isEmpty()) {
                        "#$it"
                    } else {
                        ""
                    }
                }
            }
        }
        detailViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visible { it }
        }
        detailViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val error = it ?: return@observe
            viewBinding.progress.visible { false }

            Snackbar
                .make(
                    viewBinding.root,
                    error,
                    Snackbar.LENGTH_LONG
                )
                .setAction(getString(R.string.repeat_text)) {
                    currentMovie?.let { detailViewModel.movieDetail(currentMovie) }
                }
                .also {
                    it.view.also {
                        (it.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?)?.maxLines =
                            HomeFragment.MAX_LINES
                    }
                }
                .show()
        }

        //При создании фрагмента передадим во вьюмодель уже ранее полученые данные фильма
        currentMovie?.also {
            detailViewModel.loadMovieLocal(it)
            detailViewModel.movieDetail(it)
        }
    }
}