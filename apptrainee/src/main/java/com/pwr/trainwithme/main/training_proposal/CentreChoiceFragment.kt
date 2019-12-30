package com.pwr.trainwithme.main.training_proposal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.R
import com.pwr.trainwithme.adapters.CentreOverviewAdapter
import com.pwr.commonplatform.data.*
import com.pwr.commonplatform.data.model.trainee.CentreOverview
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_centre_choice.*

/**
 * A simple [Fragment] subclass.
 */
class CentreChoiceFragment : Fragment() {

    companion object {
        const val TAG = "CentreChoiceFragment"
    }

    private val proposalViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }
    private val adapter by lazy {
        CentreOverviewAdapter(
            requireContext(), listOf(), this::onCentreSelected, this::onMoreInfoClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_centre_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_centre_choice_fragment_centres.layoutManager =
            LinearLayoutManager(requireContext())
        recycler_centre_choice_fragment_centres.adapter = adapter

        button_centre_choice_fragment_next.isEnabled = false

        // Observe to centres changes
        proposalViewModel.centresOverviews.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> renderUI(it.data)
                Result.Status.ERROR -> {
                    button_centre_choice_fragment_next.isEnabled = false
                    snack(it.message ?: getString(R.string.unknown_error))
                }
            }
        }

        button_centre_choice_fragment_back.setOnClickListener {
            findNavController().navigateUp()
        }

        button_centre_choice_fragment_next.setOnClickListener {
            findNavController().navigate(R.id.action_centreChoiceFragment_to_proposalConclusionFragment)
        }
    }

    private fun renderUI(centres: List<CentreOverview>?) {
        if (centres == null) snack(getString(R.string.null_data_error))
        else {
            adapter.items = centres

            // enable only if the centre is in centre id overviews
            proposalViewModel.centreIdLiveData.observe(viewLifecycleOwner) { id ->
                button_centre_choice_fragment_next.isEnabled = id in centres.map { it.id }
            }
        }
    }

    private fun onCentreSelected(centreOverview: CentreOverview) {
        snack("${getString(R.string.selected)}: ${centreOverview.name}")
        proposalViewModel.setCentreIdAndName(centreOverview.id, centreOverview.name)
    }

    private fun onMoreInfoClicked(centreOverview: CentreOverview) {
        // TODO implement - with different ViewModel
        snack("NOT IMPLEMENTED")
    }

}
