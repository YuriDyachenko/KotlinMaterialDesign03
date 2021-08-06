package dyachenko.kotlinmaterialdesign03.util

import android.text.Editable
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import dyachenko.kotlinmaterialdesign03.R
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "yyyy-MM-dd"

fun Date.format(): String = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
    .format(this)

fun Date.subDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, -days)
    return calendar.time
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length)
        .setAction(actionText, action)
        .show()
}

fun FragmentManager.addFragmentWithBackStack(fragment: Fragment) = this.apply {
    beginTransaction()
        .add(R.id.container, fragment)
        .addToBackStack(null)
        .commit()
}

fun Menu.hideAllItems() {
    for (i: Int in 0 until this.size()) {
        this.getItem(i).let {
            if (it.isVisible) {
                it.isVisible = false
            }
        }
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
