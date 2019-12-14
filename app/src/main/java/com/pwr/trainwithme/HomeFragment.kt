package com.pwr.trainwithme


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.SummaryAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), SummaryAdapter.OnSummarySelectedListener {

    private val trainings = MockData.trainingsSummaries
    private val passes = MockData.passesSummaries

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcoming_trainings_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcoming_trainings_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                trainings,
                this,
                cardType = SummaryAdapter.MEDIUM
            )

        passes_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        passes_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                passes,
                this,
                SummaryAdapter.WIDE
            )
    }

    override fun onSummarySelected(summary: Summarisable) {
        // TODO implement
    }
}
