package com.pwr.apptrainer.main.proposal_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.apptrainer.R
import com.pwr.apptrainer.auth.adapters.TrainerTrainingOverviewAdapter
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingOverview
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_new_proposals.*

/**
 * A simple [Fragment] subclass.
 */
class NewProposalsFragment : Fragment() {

    private val adapter by lazy {
        TrainerTrainingOverviewAdapter(
            requireContext(),
            listOf(),
            this::onProposalSelected,
            this::onProposalSwipeLeft,
            this::onProposalSwipeRight
        )
    }
    private val proposalManagementViewModel by lazy {
        ViewModelProviders.of(requireActivity())[ProposalManagementViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_proposals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proposals_recycler.layoutManager = LinearLayoutManager(requireContext())
        proposals_recycler.adapter = adapter

        proposalManagementViewModel.refreshProposals()
        proposalManagementViewModel.trainingProposals.observe(viewLifecycleOwner){
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> renderProposals(it.data)
                Result.Status.ERROR -> snack(it.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun renderProposals(proposals: List<TrainerTrainingOverview>?){
        when(proposals){
            null -> snack(getString(R.string.null_data_error))
            else -> adapter.items = proposals
        }
    }

    private fun onProposalSelected(trainingOverview: TrainerTrainingOverview){
        // TODO implement
    }

    private fun onProposalSwipeLeft(trainingOverview: TrainerTrainingOverview){
        // TODO implement
    }

    private fun onProposalSwipeRight(trainingOverview: TrainerTrainingOverview){
        // TODO implement
    }


}