package softing.ubah4ukdev.moviesinfosearcher.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import softing.ubah4ukdev.moviesinfosearcher.Constants
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentDetailBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
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
        val toolbar: Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        toolbar?.menu?.clear()
        if (arguments != null) {
            val currentMovie: Movie? = arguments?.getParcelable<Movie>(Constants.MOVIE_ARG)
            viewBinding.titleMovie.text = currentMovie?.title
            viewBinding.titleOverview.text = currentMovie?.overview
            viewBinding.releaseDateMovie.text =
                "${getString(R.string.release_date)} ${currentMovie?.releaseDate}"

            Glide.with(viewBinding.moviePoster)
                .load(currentMovie?.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_no_image)
                .into(viewBinding.moviePoster)

            Glide.with(viewBinding.movieBackdrop)
                .load(currentMovie?.backdropPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_no_image)
                .into(viewBinding.movieBackdrop)
        }
    }
}

class DetailViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(resourceProvider) as T
}