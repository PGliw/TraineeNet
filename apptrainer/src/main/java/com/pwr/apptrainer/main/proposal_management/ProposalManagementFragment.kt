package com.pwr.apptrainer.main.proposal_management

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.pwr.apptrainer.R
import com.pwr.commonplatform.utils.snack

abstract class ProposalManagementFragment : Fragment() {

    val proposalManagementViewModel by lazy {
        ViewModelProviders.of(requireActivity())[ProposalManagementViewModel::class.java]
    }

    fun DialogInterface.OnClickListener.acceptProposal(){
        proposalManagementViewModel.acceptProposal().observe(viewLifecycleOwner){
            when(it.status){
                com.pwr.commonplatform.data.Result.Status.LOADING -> snack(getString(R.string.loading))  // TODO change to progress bar
                com.pwr.commonplatform.data.Result.Status.SUCCESS -> {
                    renderConfirmationPopUp()
                    proposalManagementViewModel.refreshProposals()
                }
                com.pwr.commonplatform.data.Result.Status.ERROR -> snack(it.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun DialogInterface.OnClickListener.renderConfirmationPopUp(){
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.confirmation_successfully_sent))
            .setPositiveButton(getString(R.string.ok), this)
            .show()
    }
}