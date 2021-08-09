package dyachenko.kotlinmaterialdesign03.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.databinding.MarsFragmentBinding
import dyachenko.kotlinmaterialdesign03.model.mars.MarsPhotosResponseData
import dyachenko.kotlinmaterialdesign03.util.*
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.mars.MarsViewModel
import java.util.*

class MarsFragment : Fragment() {
    private var _binding: MarsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    private var daysBefore = START_DAYS_BEFORE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<AppState> { renderData(it) }
        viewModel.setStringResources { id: Int -> getString(id) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        getData()
    }

    private fun getData() {
        viewModel.getMarsPhotosFromServer(Date().subDays(daysBefore).format())
    }

    private fun renderData(data: AppState) = with(binding) {
        when (data) {
            is AppState.Success<*> -> {
                val responseData = data.responseData as MarsPhotosResponseData
                if (responseData.photos.isEmpty()) {
                    if (daysBefore < MAX_DAYS_BEFORE) {
                        daysBefore++
                        getData()
                    } else {
                        daysBefore = START_DAYS_BEFORE
                        marsLoadingLayout.hide()
                        marsImageView.setImageResource(R.drawable.ic_no_photo_vector)
                    }
                } else {
                    val url = responseData.photos.first().url
                    val earthDate = responseData.photos.first().earth_date
                    marsDateTextView.text = earthDate
                    loadImageWithCallback(
                        url,
                        marsImageView,
                        marsLoadingLayout,
                        marsRootView
                    ) { getData() }
                }
            }
            is AppState.Loading -> {
                marsLoadingLayout.show()
            }
            is AppState.Error -> {
                whenError(
                    data.error.message,
                    marsLoadingLayout,
                    marsRootView
                ) { getData() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val MAX_DAYS_BEFORE = 30
        const val START_DAYS_BEFORE = 0
    }
}