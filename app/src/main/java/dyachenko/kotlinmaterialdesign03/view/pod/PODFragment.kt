package dyachenko.kotlinmaterialdesign03.view.pod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.databinding.PodBottomSheetLayoutBinding
import dyachenko.kotlinmaterialdesign03.databinding.PodFragmentBinding
import dyachenko.kotlinmaterialdesign03.model.pod.PODResponseData
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.util.*
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.pod.PODViewModel
import java.util.*

class PODFragment : Fragment() {
    private var _binding: PodFragmentBinding? = null
    private val binding get() = _binding!!

    private var _bottomSheetBinding: PodBottomSheetLayoutBinding? = null
    private val bottomSheetBinding get() = _bottomSheetBinding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    private var isImageExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomSheetBehavior(view)
        initChips()
        initImageExpansion()

        val observer = Observer<AppState> { renderData(it) }
        viewModel.setStringResources { id: Int -> getString(id) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        getData()
    }

    private fun initImageExpansion() = with(binding) {
        podImageView.setOnClickListener {
            isImageExpanded = isImageExpanded.not()
            podImageView.imageTransform(isImageExpanded, podRootView)
        }
    }

    private fun initBottomSheetBehavior(view: View) {
        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        _bottomSheetBinding = PodBottomSheetLayoutBinding.bind(bottomSheet)
    }

    private fun initChips() = with(binding) {
        dayChipGroup.check(R.id.today_chip)

        dayChipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                it.isChecked = true
                var newDayOfPhoto = SettingsData.dayOfPhoto
                when (it.id) {
                    R.id.today_chip -> newDayOfPhoto = SettingsData.TODAY_PHOTO
                    R.id.yesterday_chip -> newDayOfPhoto = SettingsData.YESTERDAY_PHOTO
                    R.id.day_before_yesterday_chip -> newDayOfPhoto =
                        SettingsData.DAY_BEFORE_YESTERDAY_PHOTO
                }
                if (newDayOfPhoto != SettingsData.dayOfPhoto) {
                    SettingsData.dayOfPhoto = newDayOfPhoto
                    getData()
                }
            }
        }
    }

    private fun getData() {
        viewModel.getPODFromServer(Date().subDays(SettingsData.dayOfPhoto).format())
    }

    private fun renderData(data: AppState) = with(binding) {
        when (data) {
            is AppState.Success<*> -> {
                val responseData = data.responseData as PODResponseData

                if (responseData.mediaType == "image") {
                    loadImageWithCallback(
                        responseData.url,
                        podImageView,
                        podLoadingLayout,
                        podRootView
                    ) { getData() }

                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    podImageView.setImageResource(R.drawable.ic_no_photo_vector)
                    podLoadingLayout.hide()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                }

                bottomSheetBinding.apply {
                    bottomSheetDescriptionHeader.text = responseData.title
                    bottomSheetDescription.text = responseData.explanation
                }
            }
            is AppState.Loading -> {
                podLoadingLayout.show()
            }
            is AppState.Error -> {
                whenError(
                    data.error.message,
                    podLoadingLayout,
                    podRootView
                ) { getData() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}