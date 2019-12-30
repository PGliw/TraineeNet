package com.pwr.trainwithme.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.data.model.trainee.Summarisable
import com.pwr.trainwithme.adapters.SummaryAdapter

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
            && it.data != null) summaryAdapter.summaries = it.data ?: listOf()
    })
}