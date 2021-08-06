package dyachenko.kotlinmaterialdesign03.view.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.databinding.MarsFragmentBinding
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
            is AppState.SuccessMars -> {
                val responseData = data.responseData
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

                    Picasso
                        .get()
                        .load(url)
                        .placeholder(R.drawable.ic_no_photo_vector)
                        .into(marsImageView, object : Callback {
                            override fun onSuccess() {
                                marsLoadingLayout.hide()
                            }

                            override fun onError(e: Exception?) {
                                marsRootView.showSnackBar(e?.message
                                    ?: getString(R.string.error_server_msg),
                                    getString(R.string.reload_msg),
                                    { getData() })
                            }
                        })
                }
            }
            is AppState.Loading -> {
                marsLoadingLayout.show()
            }
            is AppState.Error -> {
                marsLoadingLayout.hide()
                marsRootView.showSnackBar(data.error.message ?: getString(R.string.error_msg),
                    getString(R.string.reload_msg),
                    { getData() })
            }
            is AppState.SuccessEarth -> {
            }
            is AppState.SuccessPOD -> {
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