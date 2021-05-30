package softing.ubah4ukdev.moviesinfosearcher.ui.favorites

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentFavoriteBinding

class FavoritesFragment : Fragment() {

    private var _bindingFavoriteFragment: FragmentFavoriteBinding? = null
    private val bindingFavoriteFragment get() = _bindingFavoriteFragment!!

    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _bindingFavoriteFragment = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = bindingFavoriteFragment.root

        val toolbar: Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        toolbar?.menu?.clear()
        toolbar?.inflateMenu(R.menu.favorite)
        toolbar?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_find -> {
                    Snackbar.make(
                        bindingFavoriteFragment.root,
                        resources.getString(R.string.find_text_favorite),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    return@setOnMenuItemClickListener false
                }
            }
            super.onOptionsItemSelected(item)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFavoriteFragment = null
    }
}