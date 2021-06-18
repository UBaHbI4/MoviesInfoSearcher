package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHomeBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.MoviesRetrofitRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieStorage
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
        HomeViewModelFactory(
            ResourceProvider(requireActivity().application),
            MoviesRetrofitRepositoryImpl,
            MovieStorage(requireActivity().application)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            homeViewModel.getMovies()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).also {
                    it.navigate(R.id.nav_contact)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.need_permission_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_contact -> {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).also {
                        it.navigate(R.id.nav_contact)
                    }
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                        Snackbar.make(
                            viewBinding.root,
                            getString(R.string.need_permission_text),
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction(
                            getString(R.string.grant)
                        ) {
                            permissionRequest.launch(Manifest.permission.READ_CONTACTS)
                        }.show()
                    } else {
                        permissionRequest.launch(Manifest.permission.READ_CONTACTS)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
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