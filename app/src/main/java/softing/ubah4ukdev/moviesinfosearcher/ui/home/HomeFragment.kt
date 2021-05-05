package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHomeBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.AppState
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.IMovieClickable
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.IMovieLongClickable
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.MoviesAdapter

class HomeFragment : Fragment(), IMovieClickable, IMovieLongClickable {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapterShowingMovies: MoviesAdapter
    private lateinit var adapterExpectedMovies: MoviesAdapter

    private var _bindingHomeFragment: FragmentHomeBinding? = null
    private val bindingHomeFragment get() = _bindingHomeFragment!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _bindingHomeFragment = FragmentHomeBinding.inflate(inflater, container, false)
        val view = bindingHomeFragment.root

        init()
        return view
    }

    private fun init() {
        //RV для текущих фильмов
        val moviesShowingRV: RecyclerView = bindingHomeFragment.moviesShowingRV
        //RV для ожидаемых
        val moviesExpectedRV: RecyclerView = bindingHomeFragment.moviesExpectedRV
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

        homeViewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        homeViewModel.getFilmsPlayingNow()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                _bindingHomeFragment?.progress?.visibility = View.GONE
                adapterShowingMovies.clear()
                adapterShowingMovies.addItems(appState.moviesPlayNow)
                adapterShowingMovies.notifyDataSetChanged()

                adapterExpectedMovies.clear()
                adapterExpectedMovies.addItems(appState.moviesUpComing)
                adapterExpectedMovies.notifyDataSetChanged()

                bindingHomeFragment.inExpectedTV.visibility = View.VISIBLE
                bindingHomeFragment.inShowingTV.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                _bindingHomeFragment?.progress?.visibility = View.GONE

                adapterShowingMovies.clear()
                adapterShowingMovies.notifyDataSetChanged()

                adapterExpectedMovies.clear()
                adapterExpectedMovies.notifyDataSetChanged()

                bindingHomeFragment.inExpectedTV.visibility = View.GONE
                bindingHomeFragment.inShowingTV.visibility = View.GONE

                val snake: Snackbar = Snackbar
                    .make(
                        bindingHomeFragment.root,
                        appState.error.message ?: getString(R.string.error_text),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(getString(R.string.repeat_text)) { homeViewModel.getFilmsPlayingNow() }
                    .setDuration(10000)

                //Сделаем больше строк у снекбара, чтобы все влезло :)
                val snackBarView: View = snake.view
                val textView: TextView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.maxLines = 5
                snake.show()
            }
            AppState.Loading -> {
                _bindingHomeFragment?.progress?.visibility = View.VISIBLE
            }
        }
    }

    fun showMessage(message: String) {
        makeText(context, message, Toast.LENGTH_LONG).show()
    }

    //Кликнули по фильму
    override fun onMovieClick(position: Int, movie: Movie) {
        homeViewModel.onMovieClick(position, movie)
    }

    //Сделали длительное нажатие по фильму
    override fun onMovieLongClick(position: Int, movie: Movie) {
        homeViewModel.onMovieClick(position, movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingHomeFragment = null
    }
}