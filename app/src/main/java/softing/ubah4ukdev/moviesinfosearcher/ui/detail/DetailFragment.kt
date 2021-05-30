package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentDetailBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewBinding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )

    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(ResourceProvider(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val currentMovie: Movie? = arguments?.getParcelable(HomeFragment.MOVIE_ARG)
            with(viewBinding) {
                movieTitle.text = currentMovie?.title
                movieOverview.text = currentMovie?.overview
                "${getString(R.string.release_date)} ${currentMovie?.releaseDate}".also {
                    movieReleaseDate.text = it
                }

                Glide.with(moviePoster)
                    .load(currentMovie?.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.ic_no_image)
                    .into(moviePoster)

                Glide.with(movieBackdrop)
                    .load(currentMovie?.backdropPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.ic_no_image)
                    .into(movieBackdrop)
            }
        }
    }
}