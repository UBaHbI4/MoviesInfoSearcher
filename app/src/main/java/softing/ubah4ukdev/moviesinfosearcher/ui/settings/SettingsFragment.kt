package softing.ubah4ukdev.moviesinfosearcher.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _bindingSettingFragment: FragmentSettingsBinding? = null
    private val bindingSettingFragment get() = _bindingSettingFragment!!

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)

        _bindingSettingFragment = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = bindingSettingFragment.root

        val toolbar: Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        toolbar?.menu?.clear()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSettingFragment = null
    }
}