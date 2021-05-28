package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.Constants
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHomeBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.IMovieClickable
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.IMovieLongClickable
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.MoviesAdapter
import softing.ubah4ukdev.moviesinfosearcher.viewBinding


class HomeFragment : Fragment(R.layout.fragment_home), IMovieClickable, IMovieLongClickable {

    private lateinit var adapterShowingMovies: MoviesAdapter
    private lateinit var adapterExpectedMovies: MoviesAdapter

    private val viewBinding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(ResourceProvider(requireActivity().application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            homeViewModel.getFilms()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        //RV для текущих фильмов
        val moviesShowingRV: RecyclerView = viewBinding.moviesShowingRV
        //RV для ожидаемых
        val moviesExpectedRV: RecyclerView = viewBinding.moviesExpectedRV
        //Адаптер для текущих фильмов
        adapterShowingMovies = MoviesAdapter(this, this)
        //Адаптер для ожидаемых фильмов
        adapterExpectedMovies = MoviesAdapter(this, this)

        moviesShowingRV.adapter = adapterShowingMovies
        moviesExpectedRV.adapter = adapterExpectedMovies

        //Подпишемся на изменение выбранного фильма и отобразим в Snackbar выбраннй фильм
        homeViewModel.titleMovie.observe(viewLifecycleOwner, {
            showMessage("${resources.getString(R.string.movie_selected)} $it")
        })

        val toolbar: Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        toolbar?.menu?.clear()
        toolbar?.inflateMenu(R.menu.favorite)
        toolbar?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_find -> {
                    showMessage(resources.getString(R.string.find_text))
                    return@setOnMenuItemClickListener false
                }
            }
            super.onOptionsItemSelected(item)
        }

        homeViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val error = it ?: return@observe
            viewBinding.progress.visibility = View.GONE
            adapterShowingMovies.clear()
            adapterShowingMovies.notifyDataSetChanged()

            adapterExpectedMovies.clear()
            adapterExpectedMovies.notifyDataSetChanged()

            viewBinding.inExpectedTV.visibility = View.GONE
            viewBinding.inShowingTV.visibility = View.GONE

            val snake: Snackbar = Snackbar
                .make(
                    viewBinding.root,
                    error,
                    Snackbar.LENGTH_INDEFINITE
                )
                .setAction(getString(R.string.repeat_text)) { homeViewModel.getFilms() }

            //Сделаем больше строк у снекбара, чтобы все влезло :)
            val snackBarView: View = snake.view
            //snackBarView.setBackgroundColor(Color.GRAY)
            val textView: TextView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 5
            snake.show()
        }

        homeViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.moviewPlayNowLiveData.observe(viewLifecycleOwner) {
            adapterShowingMovies.clear()
            val movies = it ?: return@observe
            adapterShowingMovies.addItems(movies)
            adapterShowingMovies.notifyDataSetChanged()
            viewBinding.inShowingTV.visibility = View.VISIBLE
        }

        homeViewModel.moviewUpComingLiveData.observe(viewLifecycleOwner) {
            adapterExpectedMovies.clear()
            val movies = it ?: return@observe
            adapterExpectedMovies.addItems(movies)
            adapterExpectedMovies.notifyDataSetChanged()
            viewBinding.inExpectedTV.visibility = View.VISIBLE
        }
    }

    fun showMessage(message: String) {
        makeText(context, message, Toast.LENGTH_LONG).show()
    }

    //Кликнули по фильму
    override fun onMovieClick(position: Int, movie: Movie) {
        //homeViewModel.onMovieClick(position, movie)
        val bundle = Bundle()
        bundle.putParcelable(Constants.MOVIE_ARG, movie)
        val navController: NavController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_detail, bundle)
    }

    //Сделали длительное нажатие по фильму
    override fun onMovieLongClick(position: Int, movie: Movie) {
        homeViewModel.onMovieClick(position, movie)
    }
}

class HomeViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(resourceProvider) as T
}