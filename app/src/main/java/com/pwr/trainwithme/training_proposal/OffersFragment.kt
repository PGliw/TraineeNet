package com.pwr.trainwithme.training_proposal


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pwr.trainwithme.R
import com.pwr.trainwithme.adapters.SummaryAdapter
import com.pwr.trainwithme.utils.initAndObserve
import kotlinx.android.synthetic.main.fragment_offers.*
import java.text.DateFormat
import java.util.*


class OffersFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val TAG = "OffersFragment"
    }

    private val proposalViewModel: TrainingProposalViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_fragment_offers_search.setOnClickListener {
            navigateNext()
        }

        sports_recycler.initAndObserve(
            requireContext(), viewLifecycleOwner, proposalViewModel.sportsSummaries, SummaryAdapter.MEDIUM
        ){
            proposalViewModel.sportID = it.id
            navigateNext()
        }

        trainers_recycler.initAndObserve(
            requireContext(), viewLifecycleOwner, proposalViewModel.trainersSummaries, SummaryAdapter.THIN
        ){
            findNavController().navigate(R.id.action_offersFragment_to_searchFragment)
            navigateNext()
        }

        objects_recycler.initAndObserve(
            requireContext(), viewLifecycleOwner, proposalViewModel.centresSummaries, SummaryAdapter.MEDIUM
        ){
            proposalViewModel.centreID = it.id
            navigateNext()
        }

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
            ).show()
        }

        proposalViewModel.day.observe(viewLifecycleOwner, Observer {
            val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
            edit_text_date.setText(dateFormat.format(it))
        })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        proposalViewModel.day.value = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.time
    }

    private fun navigateNext(){
        findNavController().navigate(R.id.action_offersFragment_to_searchFragment)
    }


}
