package dyachenko.kotlinmaterialdesign03.view.earth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dyachenko.kotlinmaterialdesign03.databinding.EarthFragmentBinding
import dyachenko.kotlinmaterialdesign03.model.earth.EarthResponseData
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.util.*
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.earth.EarthViewModel
import java.util.*

class EarthFragment : Fragment() {
    private var _binding: EarthFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    private var isImageExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EarthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageExpansion()
        initViews()

        val observer = Observer<AppState> { renderData(it) }
        viewModel.setStringResources { id: Int -> getString(id) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        getData()
    }

    private fun initImageExpansion() = with(binding) {
        earthImageView.setOnClickListener {
            isImageExpanded = isImageExpanded.not()
            earthImageView.imageTransform(isImageExpanded, earthRootView)
        }
    }

    private fun initViews() = with(binding) {
        earthLatEditText.text = "${SettingsData.earthLat}".toEditable()
        earthLonEditText.text = "${SettingsData.earthLon}".toEditable()
        earthDimEditText.text = "${SettingsData.earthDim}".toEditable()
        earthApplyChip.setOnClickListener {
            val lat = earthLatEditText.text.toString().toFloat()
            val lon = earthLonEditText.text.toString().toFloat()
            val dim = earthDimEditText.text.toString().toFloat()
            if ((SettingsData.earthLat != lat)
                || (SettingsData.earthLon != lon)
                || (SettingsData.earthDim != dim)
            ) {
                SettingsData.earthLat = lat
                SettingsData.earthLon = lon
                SettingsData.earthDim = dim
                writeSettings()
                getData()
            }
        }
    }

    private fun writeSettings() {
        activity?.let {
            with(
                it.getSharedPreferences(SettingsData.PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
            ) {
                putFloat(SettingsData.CURRENT_EARTH_LAT, SettingsData.earthLat)
                putFloat(SettingsData.CURRENT_EARTH_LON, SettingsData.earthLon)
                putFloat(SettingsData.CURRENT_EARTH_DIM, SettingsData.earthDim)
                apply()
            }
        }
    }

    private fun getData() {
        viewModel.getEarthPhotoFromServer(
            Date().format(),
            SettingsData.earthLon,
            SettingsData.earthLat,
            SettingsData.earthDim
        )
    }

    private fun renderData(data: AppState) = with(binding) {
        when (data) {
            is AppState.Success<*> -> {
                loadImageWithCallback(
                    (data.responseData as EarthResponseData).url,
                    earthImageView,
                    earthLoadingLayout,
                    earthRootView
                ) { getData() }
            }
            is AppState.Loading -> {
                earthLoadingLayout.show()
            }
            is AppState.Error -> {
                whenError(
                    data.error.message,
                    earthLoadingLayout,
                    earthRootView
                ) { getData() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}