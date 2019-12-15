package com.pwr.trainwithme


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.SummaryAdapter
import kotlinx.android.synthetic.main.fragment_offers.*
import java.util.*


class OffersFragment : Fragment(), SummaryAdapter.OnSummarySelectedListener, DatePickerDialog.OnDateSetListener {

    companion object{
        const val TAG = "OffersFragment"
    }

    private val proposalViewModel : TrainingProposalViewModel by lazy {
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

        sports_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val sportsAdapter = SummaryAdapter(
            requireContext(),
            listOf(),
            this,
            SummaryAdapter.MEDIUM
        )
        sports_recycler.adapter = sportsAdapter
        proposalViewModel.sportsSummaries.observe(viewLifecycleOwner,
            Observer<List<Summarisable>> { observedSportSummaries ->
                sportsAdapter.summaries = observedSportSummaries
                Log.d(TAG, observedSportSummaries.toString())
                Log.d(TAG,sportsAdapter.summaries.toString())
            })

        trainers_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trainers_recycler.adapter =
            SummaryAdapter(requireContext(), trainers, this)
        objects_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        objects_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                sportCentres,
                this,
                SummaryAdapter.MEDIUM
            )

        edit_text_date.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this, // listener to data set
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH] // Initially set current date TODO show date from viewModel
            ).show() // show dialog
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // TODO set date in view model
    }

    override fun onSummarySelected(summary: Summarisable) {
        findNavController().navigate(R.id.action_offersFragment_to_searchFragment)
    }

}
