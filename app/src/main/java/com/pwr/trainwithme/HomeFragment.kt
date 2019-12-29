package com.pwr.trainwithme


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.SummaryAdapter
import com.pwr.trainwithme.adapters.TrainingSummaryAdapter
import com.pwr.trainwithme.data.MockData
import com.pwr.trainwithme.data.Result
import com.pwr.trainwithme.data.TrainingSummary
import com.pwr.trainwithme.utils.snack
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_sport_choice.*

class HomeFragment : Fragment() {

    private val trainings = MockData.trainingsSummaries
    private val passes = MockData.passesSummaries
    private val trainingsManagementViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingsManagementViewModel::class.java]
    }
    private val trainingSummaryAdapter by lazy {
        TrainingSummaryAdapter(
            requireContext(), listOf()
        ) {
            snack(it.centreName)
        }
    }

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
        upcoming_trainings_recycler.adapter = trainingSummaryAdapter

        trainingsManagementViewModel.trainingsSummaries.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> if (it.data != null) renderTrainingsSummaries(it.data)
                else snack(getString(R.string.null_data_error))
                Result.Status.ERROR -> {
                    snack(it.message ?: getString(R.string.unknown_error))
                }
            }
        }


        passes_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        passes_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                passes,
                {}, // TODO implement
                SummaryAdapter.WIDE
            )
    }

    private fun renderTrainingsSummaries(summaries: List<TrainingSummary>) {
        trainingSummaryAdapter.items = summaries
    }

}
