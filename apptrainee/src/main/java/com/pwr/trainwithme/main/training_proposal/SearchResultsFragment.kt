package com.pwr.trainwithme.main.training_proposal


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.R
import com.pwr.commonplatform.utils.VerticalScrollHider
import com.pwr.trainwithme.adapters.TrainerOverviewAdapter
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_search_results.*
import androidx.lifecycle.observe
import com.pwr.commonplatform.data.model.trainee.TrainerOverview

/**
 * A simple [Fragment] subclass.
 */
class SearchResultsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }
    private val adapter by lazy {
        TrainerOverviewAdapter(
            requireContext(), listOf()
        ) {
            viewModel.setTrainerIdAndNameAndPhotoUrl(
                it.id,
                "${it.firstName} ${it.lastName}",
                it.photoUrl
            )
            navigateNext()
        }
    }
    private var sortByIndex = 0
    private var filterIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel apply search

        offer_recycler.layoutManager = LinearLayoutManager(requireContext())
        offer_recycler.adapter = adapter
        offer_recycler.addOnScrollListener(
            VerticalScrollHider(
                button_sort,
                button_filter
            )
        )
        viewModel.trainersOverviews.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> renderTrainersOverviews(it.data)
                Result.Status.ERROR -> snack(it.message ?: getString(R.string.unknown_error))
            }
        }

        button_sort.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.sort)
                .setSingleChoiceItems(R.array.sort_options, sortByIndex)
                { dialog, which ->
                    sortByIndex = which
                    dialog.dismiss()
                }
                .create()
            builder.show()
        }

        button_filter.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.filter)
                .setSingleChoiceItems(R.array.filter_options, filterIndex) { dialog, which ->
                    filterIndex = which
                    dialog.dismiss()
                }
            builder.show()
        }
    }

    private fun renderTrainersOverviews(trainerOverviews : List<TrainerOverview>?){
        if(trainerOverviews == null) snack(getString(R.string.null_data_error))
        else adapter.items = trainerOverviews
    }

    private fun navigateNext() {
        findNavController().navigate(R.id.action_searchFragment_to_appointmentFragment)
    }
}
