package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.app.App
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentDetailBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.LocalRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.MoviesRetrofitRepositoryImpl
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
            MoviesRetrofitRepositoryImpl,
            LocalRepositoryImpl.getInstance(App.getHistoryDao())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMovie: Movie? = requireArguments().getParcelable(HomeFragment.MOVIE_ARG)

        currentMovie?.let {
            detailViewModel.addToHistory(currentMovie)
        }
        //???????? ???? ???????????? ?????? ?????????????????????????????? ???????????????? ?? ????????????
        viewBinding.video.setOnClickListener {
            context?.let {
                val msg: String = it.getString(R.string.movie_trailer_message)
                view.showSnakeBar(msg, Snackbar.LENGTH_SHORT)
            }
        }

        //???????? ???? ???????????? ?????? ???????????????? ???? ???????????????? ???????????????? ????????????
        viewBinding.webSite.setOnClickListener {
            context?.let {
                val msg: String = it.getString(R.string.movie_home_page_message)
                view.showSnakeBar(msg, Snackbar.LENGTH_SHORT)
            }
        }

        //???????? ???? ???????????? ???????????????????? ?????????????? ?? ????????????
        viewBinding.commentAdd.setOnClickListener {
            context?.let {
                currentMovie?.let {
                    commentEditDialog(it, it.comment)
                }
            }
        }

        //???????????????? ?????? ?????????????????????? ?????????????????? ???????????? ?? ????????????, ?????????????? ?????? ?????????????????? ?? ???????????? ??????????????
        // ?? ???????????????? ?? ??????????????????????
        detailViewModel.localMovieLiveData.observe(viewLifecycleOwner) {
            with(viewBinding) {
                movieTitle.text = it?.title
                movieOverview.text = it?.overview
                rating.text = it?.voteAverage.toString()
                movieReleaseDate.text = it?.releaseDate
                movieComment.text = it?.comment

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

        //???????????????? ?????? ?????????????????????? ???????????????????????? ?????????? ?????????????????? ???????????? ?????? ???????????????????? ????????????
        detailViewModel.movieLiveData.observe(viewLifecycleOwner) {
            with(viewBinding) {
                movieBudget.text = it.budget.toString()
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

        if (savedInstanceState == null) {
            //?????? ???????????????? ?????????????????? ?????????????????? ???? ?????????????????? ?????? ?????????? ?????????????????? ???????????? ????????????
            currentMovie?.also {
                detailViewModel.loadMovieLocal(it)
                detailViewModel.movieDetail(it)
            }
        }
    }

    private fun commentEditDialog(movie: Movie, oldComment: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.dialog_title))
            setMessage(getString(R.string.dialog_message) + "'${movie.title}'")
        }

        val input = EditText(requireContext()).apply {
            hint = getString(R.string.dialog_hint)
            setText(oldComment)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        with(builder) {
            setView(input)
            setPositiveButton(getString(R.string.dialog_positive_btn_title)) { dialog, which ->
                input.text.toString().let {
                    detailViewModel.addComment(movie, it)
                    viewBinding.movieComment.text = it
                }
            }
            setNegativeButton(getString(R.string.dialog_negative_btn_title)) { dialog, which -> dialog.cancel() }
            show()
        }
    }
}