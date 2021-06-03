package softing.ubah4ukdev.moviesinfosearcher.ui.favorites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentFavoriteBinding
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.showSnakeBar
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val viewBinding: FragmentFavoriteBinding by viewBinding(
        FragmentFavoriteBinding::bind
    )

    private val detailViewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory(ResourceProvider(requireActivity().application))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favorite, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> {
                view?.showSnakeBar(resources.getString(R.string.send_text), Snackbar.LENGTH_SHORT)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}