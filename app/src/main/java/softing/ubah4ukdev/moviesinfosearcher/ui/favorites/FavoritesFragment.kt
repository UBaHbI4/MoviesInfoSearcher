package softing.ubah4ukdev.moviesinfosearcher.ui.favorites

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentFavoriteBinding
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private val viewBinding: FragmentFavoriteBinding by viewBinding(
        FragmentFavoriteBinding::bind
    )

    private val detailViewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory(ResourceProvider(requireActivity().application))
    }
}