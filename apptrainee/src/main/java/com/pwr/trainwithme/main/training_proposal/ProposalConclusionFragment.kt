package com.pwr.trainwithme.main.training_proposal


import android.app.AlertDialog
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
import com.bumptech.glide.Glide
import com.ncorti.slidetoact.SlideToActView
import com.pwr.commonplatform.data.Result
import com.pwr.trainwithme.R
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_proposal_conclusion.*

/**
 * A simple [Fragment] subclass.
 */
class ProposalConclusionFragment : Fragment(), SlideToActView.OnSlideCompleteListener,
    DialogInterface.OnClickListener {

    companion object {
        const val TAG = "ProposalConcFrag"
    }

    private val proposalViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal_conclusion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // render trainer
        Glide.with(requireActivity())
            .load(proposalViewModel.trainerPhotoUrl)
            .placeholder(R.drawable.simple_loading_img_placeholder)
            .error(R.drawable.error_image_placeholder)
            .into(image_fragment_proposal_conclusion_trainer)
        text_fragment_proposal_conclusion_trainer_name.text = proposalViewModel.trainerName

        // render sport
        button_fragment_proposal_conclusion_sport.text = proposalViewModel.sportName
        button_fragment_proposal_conclusion_sport.setOnClickListener {
            findNavController().navigate(R.id.action_proposalConclusionFragment_to_appointmentFragment)
        }

        // render date
        button_fragment_proposal_conclusion_date.text = proposalViewModel.timeRangeFormatted
        button_fragment_proposal_conclusion_date.setOnClickListener {
            findNavController().navigate(R.id.action_proposalConclusionFragment_to_dateChoiceFragment)
        }

        // render centre
        button_fragment_proposal_conclusion_center.text = proposalViewModel.centreName
        button_fragment_proposal_conclusion_center.setOnClickListener {
            findNavController().navigate(R.id.action_proposalConclusionFragment_to_centreChoiceFragment2)
        }

        slide_fragment_proposal_conclusion_send_proposal.onSlideCompleteListener = this
    }

    override fun onSlideComplete(view: SlideToActView) {
        val result = proposalViewModel.sendTrainingProposal()
        result.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading))
                Result.Status.ERROR -> {
                    snack(it.message ?: getString(R.string.unknown_error))
                    Log.e(TAG, it.message)
                    slide_fragment_proposal_conclusion_send_proposal.resetSlider()
                }
                Result.Status.SUCCESS -> renderConfirmationPopup()
            }
        }
    }

    private fun renderConfirmationPopup() {
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.training_proposal_sent_message)
            .setPositiveButton(R.string.ok, this)
            .show()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        dialog?.dismiss()
        findNavController().navigate(R.id.action_proposalConclusionFragment_to_homeFragment)
    }
}
