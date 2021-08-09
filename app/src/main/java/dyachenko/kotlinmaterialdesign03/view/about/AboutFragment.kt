package dyachenko.kotlinmaterialdesign03.view.about

import android.os.Bundle
import android.view.*
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.hideAllItems()
    }
}