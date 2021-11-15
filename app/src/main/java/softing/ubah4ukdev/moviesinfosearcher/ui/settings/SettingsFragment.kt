package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentSettingsBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.storage.MovieStorage
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewBinding: FragmentSettingsBinding by viewBinding(
        FragmentSettingsBinding::bind
    )

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(
            ResourceProvider(requireActivity().application),
            MovieStorage(requireActivity().application)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.adultAccess.setOnCheckedChangeListener { buttonView, isChecked ->
            settingsViewModel.onAdultEnableChanged(isChecked)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            settingsViewModel.adult.collect {
                viewBinding.adultAccess.isChecked = it
            }
        }
    }
}