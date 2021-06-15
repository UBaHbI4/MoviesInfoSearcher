package softing.ubah4ukdev.moviesinfosearcher.ui.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHistoryBinding
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewBinding: FragmentHistoryBinding by viewBinding(
        FragmentHistoryBinding::bind
    )

    private val detailViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(ResourceProvider(requireActivity().application))
    }
}