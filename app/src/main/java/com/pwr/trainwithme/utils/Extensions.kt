package com.pwr.trainwithme.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pwr.trainwithme.data.Summarisable
import com.pwr.trainwithme.adapters.SummaryAdapter
import com.pwr.trainwithme.data.MockData
import com.pwr.trainwithme.data.Result

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

fun RecyclerView.initAndObserve(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    summaryLiveData: LiveData<Result<List<Summarisable>>>,
    cardType: Int,
    onSummarySelected: (Summarisable) -> Unit
) {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    val summaryAdapter = SummaryAdapter(
        context, listOf(), onSummarySelected, cardType
    )
    adapter = summaryAdapter
    summaryLiveData.observe(lifecycleOwner, Observer {
        if(it.status == Result.Status.SUCCESS
            && it.data != null) summaryAdapter.summaries = it.data
    })
}