package com.pwr.apptrainer.main.proposal_management

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwr.apptrainer.R
import com.pwr.apptrainer.adapters.TrainerTrainingOverviewAdapter
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingOverview
import com.pwr.commonplatform.utils.RecyclerSwipeCallback
import com.pwr.commonplatform.utils.snack
import com.pwr.commonplatform.utils.toast
import kotlinx.android.synthetic.main.fragment_new_proposals.*

/**
 * A simple [Fragment] subclass.
 */
class NewProposalsFragment : ProposalManagementFragment(), DialogInterface.OnClickListener {

    companion object {
        const val TAG = "NewProposalsFragment"
    }

    private val adapter by lazy {
        TrainerTrainingOverviewAdapter(
            requireContext(),
            listOf(),
            this::onProposalSelected,
            this::onProposalSwipeLeft,
            this::onProposalSwipeRight
        )
    }

    private val swipeCallback by lazy {
        object : RecyclerSwipeCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is TrainerTrainingOverviewAdapter.TrainerTrainingSummaryViewHolder) {
                    viewHolder.onSwipeLeft?.invoke()
                } else Log.e(
                    TAG,
                    "swipeCallback: viewHilder is not TrainerTrainingSummaryViewHolder"
                )
            }
        }
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
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(proposals_recycler)

        proposalManagementViewModel.refreshProposals()
        proposalManagementViewModel.trainingProposals.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> renderProposals(it.data)
                Result.Status.ERROR -> snack(it.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun renderProposals(proposals: List<TrainerTrainingOverview>?) {
        if (proposals == null) {
            snack(getString(R.string.null_data_error))
            return
        }
        adapter.items = proposals
        button_fragment_new_proposals_sort.isEnabled = true
        // TODO implement real sorting
        button_fragment_new_proposals_sort.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(
                    R.array.sort_options,
                    proposalManagementViewModel.sortOptionPosition
                ) { dialog, which ->
                    proposalManagementViewModel.sortOptionPosition = which
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.back) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        // TODO implement real filtering
        button_fragment_new_proposals_filter.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setSingleChoiceItems(R.array.filter_options, 0) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.back) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun onProposalSelected(trainingOverview: TrainerTrainingOverview) {
        proposalManagementViewModel.proposalID = trainingOverview.trainingID
        findNavController().navigate(R.id.action_newProposalsFragment_to_proposalDetailsFragment)
    }

    private fun onProposalSwipeLeft(trainingOverview: TrainerTrainingOverview) {
        proposalManagementViewModel.proposalID = trainingOverview.trainingID
        acceptProposal()
    }

    private fun onProposalSwipeRight(trainingOverview: TrainerTrainingOverview) {
        // TODO implement
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        dialog?.dismiss()
    }

}