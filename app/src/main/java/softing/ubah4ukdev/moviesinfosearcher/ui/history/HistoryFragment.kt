package softing.ubah4ukdev.moviesinfosearcher.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.app.App
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHistoryBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.localrepository.LocalRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.networkrepository.MoviesRetrofitRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.visible
import softing.ubah4ukdev.moviesinfosearcher.ui.history.adapter.MovieHistoryAdapter
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val adapterMoviesHistory by lazy { MovieHistoryAdapter() }

    private val viewBinding: FragmentHistoryBinding by viewBinding(
        FragmentHistoryBinding::bind
    )

    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(
            ResourceProvider(requireActivity().application),
            MoviesRetrofitRepositoryImpl,
            LocalRepositoryImpl.getInstance(App.getHistoryDao())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel.getHistory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val moviesRV: RecyclerView = viewBinding.moviesHistoryList
        moviesRV.adapter = adapterMoviesHistory

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            historyViewModel.loading.collect {
                viewBinding.progress.visible { it }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            historyViewModel.error
                .collect {
                    val error = it ?: return@collect
                    viewBinding.progress.visible { false }

                    adapterMoviesHistory.apply {
                        clear()
                        notifyDataSetChanged()
                    }

                    Snackbar
                        .make(
                            viewBinding.root,
                            error,
                            Snackbar.LENGTH_INDEFINITE
                        )
                        .setAction(getString(R.string.repeat_text)) { }
                        .also {
                            it.view.also {
                                (it.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?)?.maxLines =
                                    HomeFragment.MAX_LINES
                            }
                        }
                        .show()
                }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            historyViewModel.movies.collect {
                val movies = it

                if (movies != null) {
                    with(adapterMoviesHistory) {
                        clear()
                        val movies = it ?: return@collect
                        addItems(movies)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            historyViewModel.deleted.collect {
                it?.let {
                    if (it) {
                        with(adapterMoviesHistory) {
                            clear()
                            notifyDataSetChanged()
                            Snackbar
                                .make(
                                    viewBinding.root,
                                    getString(R.string.clear_history_success_text),
                                    Snackbar.LENGTH_SHORT
                                )
                                .show()
                        }
                    } else {
                        with(adapterMoviesHistory) {
                        }
                        Snackbar
                            .make(
                                viewBinding.root,
                                getString(R.string.clear_history_fail_text),
                                Snackbar.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_history -> {
                historyViewModel.clearHistory()
                true
            }
            else -> {
                false
            }
        }
    }
}