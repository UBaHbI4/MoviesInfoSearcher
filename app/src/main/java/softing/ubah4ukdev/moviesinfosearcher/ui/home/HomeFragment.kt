package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHomeBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.MoviesRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.visible
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.*
import softing.ubah4ukdev.moviesinfosearcher.viewBinding


class HomeFragment : Fragment(R.layout.fragment_home), IMovieClickable {
    companion object {
        const val MAX_LINES = 5
        const val MOVIE_ARG = "MOVIE_ARG"
    }

    private val adapterMoviesGroup by lazy { MoviesGroupAdapter(this) }

    private val viewBinding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(ResourceProvider(requireActivity().application), MoviesRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            homeViewModel.getMovies()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val moviesRV: RecyclerView = viewBinding.moviesList

        moviesRV.adapter = adapterMoviesGroup

        homeViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val error = it ?: return@observe
            viewBinding.progress.visible { false }

            adapterMoviesGroup.apply {
                clear()
                notifyDataSetChanged()
            }

            Snackbar
                .make(
                    viewBinding.root,
                    error,
                    Snackbar.LENGTH_INDEFINITE
                )
                .setAction(getString(R.string.repeat_text)) { homeViewModel.getMovies() }
                .also {
                    it.view.also {
                        (it.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?)?.maxLines =
                            MAX_LINES
                    }
                }
                .show()
        }

        homeViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visible { it }
        }

        homeViewModel.movieLiveData.observe(viewLifecycleOwner) {
            with(adapterMoviesGroup) {
                clear()
                val movies = it ?: return@observe
                addItems(movies)
                notifyDataSetChanged()
            }
        }
    }

    private fun showMessage(message: String) = makeText(context, message, Toast.LENGTH_LONG).show()

    //Кликнули по фильму
    override fun onMovieClick(position: Int, movie: Movie) {
        val bundle = Bundle().also {
            it.putParcelable(MOVIE_ARG, movie)
        }

        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).also {
            it.navigate(R.id.nav_detail, bundle)
        }
    }
}