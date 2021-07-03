package softing.ubah4ukdev.moviesinfosearcher.ui.map

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentMapsBinding
import softing.ubah4ukdev.moviesinfosearcher.domain.repositories.location.LocationRepositoryImpl
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.showSnakeBar
import softing.ubah4ukdev.moviesinfosearcher.viewBinding


class MapsFragment : Fragment(R.layout.fragment_maps) {
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val DEFAULT_LATITUDE = 56.2694000
    private val DEFAULT_LONGITUDE = 90.4993000

    private val ZOOM_DEFAULT = 15.0F

    private lateinit var mapFragment: SupportMapFragment

    private val viewBinding: FragmentMapsBinding by viewBinding(
        FragmentMapsBinding::bind
    )

    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModelFactory(
            LocationRepositoryImpl(requireActivity().application)
        )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.clear()
        googleMap.uiSettings.isZoomControlsEnabled = true

        val latitudeSafe = latitude ?: DEFAULT_LATITUDE
        val longitudeSafe = longitude ?: DEFAULT_LONGITUDE

        val position = LatLng(latitudeSafe, longitudeSafe)

        viewBinding.latitudeInput.setText(latitudeSafe.toString())
        viewBinding.longitudeInput.setText(longitudeSafe.toString())

        getAddress(latitudeSafe, longitudeSafe)?.let {
            viewBinding.address.text = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, ZOOM_DEFAULT))
            googleMap.addMarker(MarkerOptions().position(position).title(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    private fun init() {
        viewBinding.findBtn.setOnClickListener {
            try {
                latitude = viewBinding.latitudeInput.text.toString().toDouble()
                longitude = viewBinding.longitudeInput.text.toString().toDouble()
            } catch (err: Exception) {
                latitude = DEFAULT_LATITUDE
                longitude = DEFAULT_LONGITUDE
                viewBinding.root.showSnakeBar(
                    getString(R.string.error_enter_coordinats_text),
                    Snackbar.LENGTH_LONG
                )
            }

            val latitudeSafe = latitude ?: DEFAULT_LATITUDE
            val longitudeSafe = longitude ?: DEFAULT_LONGITUDE

            mapFragment.getMapAsync(callback)

            //Скроем клавиатуру
            viewBinding.longitudeInput.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewBinding.latitudeInput.onEditorAction(EditorInfo.IME_ACTION_DONE)

            getAddress(latitudeSafe, longitudeSafe)?.let {
                viewBinding.address.text = it
            }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): String? {
        return mapsViewModel.address(latitude, longitude)
    }
}