package softing.ubah4ukdev.moviesinfosearcher.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHomeBinding
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

        //Подпишемся на список фильмов, которые сейчас проигрываются
        //и в случае изменения списка обновим данные в RV
        homeViewModel.movies.observe(viewLifecycleOwner, {
            adapterShowingMovies.clear()
            adapterShowingMovies.addItems(it)
            adapterShowingMovies.notifyDataSetChanged()
        })

        //Подпишемся на список фильмов, которые ожидаются
        //и в случае изменения списка обновим данные в RV
        homeViewModel.moviesUpComing.observe(viewLifecycleOwner, {
            adapterExpectedMovies.clear()
            adapterExpectedMovies.addItems(it)
            adapterExpectedMovies.notifyDataSetChanged()
        })

        //Подпишемся на изменение выбранного фильма и отобразим в Snackbar выбраннй фильм
        homeViewModel.titleMovie.observe(viewLifecycleOwner, {
            showMessage("${resources.getString(R.string.movie_selected)} $it")
        })

        //Подпишемся, на статус загрузки, для отображения/скрытия прогрессбара
        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            _bindingHomeFragment?.progress?.visibility = it
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
        getMovies()
    }

    //Получить фильмы текущие и ожидаемые
    private fun getMovies() {
        homeViewModel.getFilmsPlayingNow()
        homeViewModel.getFilmsUpcoming()
    }

    fun showMessage(message: String) {
        Snackbar.make(bindingHomeFragment.root, message, Snackbar.LENGTH_LONG)
                .show()
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