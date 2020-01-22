package com.pwr.apptrainer.main.proposal_management


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ncorti.slidetoact.SlideToActView
import com.pwr.apptrainer.R
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.utils.snack
import com.pwr.commonplatform.utils.toast
import kotlinx.android.synthetic.main.fragment_proposal_refusal.*

/**
 * A simple [Fragment] subclass.
 */
class ProposalRefusalFragment : ProposalManagementFragment(), SlideToActView.OnSlideCompleteListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposal_refusal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slide_fragment_proposal_refusal_send_refusal.onSlideCompleteListener = this
    }

    override fun onSlideComplete(view: SlideToActView) {
        proposalManagementViewModel.refusalMessage =
            edit_text_fragment_proposal_refusal_message.text.toString()
        refuseProposal()
    }

    private fun refuseProposal() {
        proposalManagementViewModel.denyProposal().observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> onRefusalSuccessful()
                Result.Status.ERROR -> onRefusalError(it.message)
            }
        }
    }

    private fun onRefusalSuccessful() {
        toast(getString(R.string.refusal_successfully_sent))
        findNavController().navigate(R.id.action_proposalRefusalFragment_to_newProposalsFragment)
    }

    private fun onRefusalError(errorMessage: String?) {
        snack(errorMessage ?: getString(R.string.unknown_error))
        slide_fragment_proposal_refusal_send_refusal.resetSlider()
    }

}
