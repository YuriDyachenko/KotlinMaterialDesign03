package dyachenko.kotlinmaterialdesign03.view.settings

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.databinding.SettingsFragmentBinding
import dyachenko.kotlinmaterialdesign03.model.settings.SettingsData
import dyachenko.kotlinmaterialdesign03.util.hideAllItems

class SettingsFragment : Fragment() {
    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setHasOptionsMenu(true)
    }

    private fun initViews() = with(binding) {
        (radioGroup.getChildAt(SettingsData.currentTheme) as RadioButton).isChecked = true

        settingMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                val oldValue = SettingsData.currentTheme
                SettingsData.currentTheme = when (radioGroup.checkedRadioButtonId) {
                    R.id.settings_radio_button_teal -> SettingsData.THEME_TEAL
                    else -> SettingsData.THEME_PURPLE
                }
                activity?.supportFragmentManager?.popBackStack()
                if (SettingsData.currentTheme != oldValue) {
                    writeSettings()
                    activity?.recreate()
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    private fun writeSettings() {
        activity?.let {
            with(
                it.getSharedPreferences(SettingsData.PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
            ) {
                putInt(SettingsData.CURRENT_THEME_PREF_NAME, SettingsData.currentTheme)
                apply()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.hideAllItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}