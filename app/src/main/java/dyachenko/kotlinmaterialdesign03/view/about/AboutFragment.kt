package dyachenko.kotlinmaterialdesign03.view.about

import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import dyachenko.kotlinmaterialdesign03.R
import dyachenko.kotlinmaterialdesign03.util.hideAllItems

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        activity?.let {
            view.findViewById<TextView>(R.id.about_text_view).typeface =
                Typeface.createFromAsset(it.assets, "fonts/CuteAurora-PK3lZ.ttf")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.hideAllItems()
    }
}