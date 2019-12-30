package com.pwr.apptrainer.main.proposal_management


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.pwr.apptrainer.R
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.data.model.trainer.TrainerTrainingDetails
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_proposal_details.*

/**
 * A simple [Fragment] subclass.
 */
class ProposalDetailsFragment : Fragment() {

    private val proposalManagementViewModel by lazy {
        ViewModelProviders.of(requireActivity())[ProposalManagementViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Disable buttons until data is successfully fetched
        button_fragment_proposal_details_reject.isEnabled = false
        button_fragment_proposal_details_accept.isEnabled = false

        proposalManagementViewModel.proposalDetails.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> renderUI(it.data)
                Result.Status.ERROR -> snack(it.message ?: getString(R.string.unknown_error))
            }
        }
    }

    private fun renderUI(details: TrainerTrainingDetails?) {
        if (details == null) {
            snack(getString(R.string.null_data_error))
            return
        }
        // render sport title
        text_fragment_proposal_details_sport.text = details.sportName

        // render trainee(s) image(s)
        if (details.traineesSummaries.isNotEmpty()) {
            Glide.with(requireContext())
                .load(details.traineesSummaries[0].imageUrl)
                .circleCrop()
                .placeholder(R.drawable.simple_loading_img_placeholder)
                .error(R.drawable.error_image_placeholder)
                .into(image_fragment_proposal_details_trainee_1)
        }

        // render trainees count info
        val traineesCountInfo =
            "${getString(R.string.trainees)}: ${details.numberOfTrainees}/${details.traineesLimit}"
        text_fragment_proposal_details_trainees_count.text = traineesCountInfo

        // render trainees info
        text_fragment_proposal_details_trainees.text =
            details.traineesSummaries.map { it.title }.toString()

        // render centre name
        text_fragment_proposal_details_centre_name.text = details.centreName

        // render centre image
        Glide.with(requireContext())
            .load(details.centrePhotoUrl)
            .fitCenter()
            .placeholder(R.drawable.simple_loading_img_placeholder)
            .error(R.drawable.error_image_placeholder)
            .into(image_fragment_proposal_details_centre)

        // render centre coordinates
        val coordinates = "(${details.centreLatitude}, ${details.centreLongitude})"
        text_fragment_proposal_details_centre_coordinates.text =  coordinates

        // render dates
        val startDateStr = "${getString(R.string.start)}: ${details.startDateTime}"
        val endDateStr = "${getString(R.string.end)}: ${details.endDateTime}"
        text_fragment_proposal_details_date_start.text = startDateStr
        text_fragment_proposal_details_date_end.text = endDateStr

        // enable buttons and set listeners
        button_fragment_proposal_details_accept.isEnabled = true
        button_fragment_proposal_details_accept.setOnClickListener {
            // TODO viewModel.accept(details.trainingID)
            snack("Accept ${details.trainingID}")
        }

        button_fragment_proposal_details_reject.isEnabled = true
        button_fragment_proposal_details_reject.setOnClickListener {
            // TODO viewModel.accept(details.trainingID)
            snack("Reject ${details.trainingID}")
        }
    }

}
