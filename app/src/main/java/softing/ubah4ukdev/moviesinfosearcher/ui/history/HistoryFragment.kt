package softing.ubah4ukdev.moviesinfosearcher.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentHistoryBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.MoviesRetrofitRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewBinding: FragmentHistoryBinding by viewBinding(
        FragmentHistoryBinding::bind
    )

    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(
            ResourceProvider(requireActivity().application),
            MoviesRetrofitRepositoryImpl
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            historyViewModel.getHistory()
        }
    }
}