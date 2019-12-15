package com.pwr.trainwithme


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwr.trainwithme.adapters.SummaryAdapter
import kotlinx.android.synthetic.main.fragment_offers.*
import java.util.*


class OffersFragment : Fragment(), SummaryAdapter.OnSummarySelectedListener,
    DatePickerDialog.OnDateSetListener {

    companion object {
        const val TAG = "OffersFragment"
    }

    private val proposalViewModel: TrainingProposalViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }

    private val calendar = Calendar.getInstance()
    private val sports = MockData.sportsSummaries
    private val trainers = MockData.trainersSummaries
    private val sportCentres = MockData.centresSummaries

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sports_recycler.initAndObserve(
            requireContext(), this, proposalViewModel.sportsSummaries, SummaryAdapter.MEDIUM
        )

        trainers_recycler.initAndObserve(
            requireContext(), this, proposalViewModel.trainersSummaries, SummaryAdapter.THIN
        )

        objects_recycler.initAndObserve(
            requireContext(), this, proposalViewModel.centresSummaries, SummaryAdapter.MEDIUM
        )

        proposalViewModel.day.observe(viewLifecycleOwner, Observer { observedDate ->
            calendar.time = observedDate
        })

        edit_text_date.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show() // show dialog
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        proposalViewModel.day.value = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.time
    }

    override fun onSummarySelected(summary: Summarisable) {
        findNavController().navigate(R.id.action_offersFragment_to_searchFragment)
    }

    private fun RecyclerView.initAndObserve(
        context: Context,
        onSummarySelectedListener: SummaryAdapter.OnSummarySelectedListener,
        summaryLiveData: LiveData<List<Summarisable>>,
        cardType: Int
    ) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val summaryAdapter = SummaryAdapter(
            requireContext(), listOf(), onSummarySelectedListener, cardType
        )
        adapter = summaryAdapter
        summaryLiveData.observe(viewLifecycleOwner, Observer {
            summaryAdapter.summaries = it
        })
    }
}
