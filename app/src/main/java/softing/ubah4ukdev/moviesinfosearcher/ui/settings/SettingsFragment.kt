package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import softing.ubah4ukdev.moviesinfosearcher.ResourceProvider
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentSettingsBinding
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class SettingsFragment : Fragment() {

    private val viewBinding: FragmentSettingsBinding by viewBinding(
        FragmentSettingsBinding::bind
    )

    private val detailViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(ResourceProvider(requireActivity().application))
    }
}