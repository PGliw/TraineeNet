package com.pwr.commonplatform.utils

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun Fragment.toast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
fun Fragment.snack(message: String) = Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Show date picker dialog with disabled past dates choice
 */
fun Fragment.showUpcomingDatePickerDialog(calendar: Calendar, listener: DatePickerDialog.OnDateSetListener){
    DatePickerDialog(
        requireActivity(),
        listener,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    ).apply {
        datePicker.minDate = calendar.timeInMillis
        show()
    }
}