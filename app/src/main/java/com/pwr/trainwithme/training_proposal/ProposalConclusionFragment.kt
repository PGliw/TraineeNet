package com.pwr.trainwithme.training_proposal


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ncorti.slidetoact.SlideToActView
import com.pwr.trainwithme.R
import kotlinx.android.synthetic.main.fragment_proposal_conclusion.*

/**
 * A simple [Fragment] subclass.
 */
class ProposalConclusionFragment : Fragment(), SlideToActView.OnSlideCompleteListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal_conclusion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slide_fragment_proposal_conclusion_send_proposal.onSlideCompleteListener = this
    }

    override fun onSlideComplete(view: SlideToActView) {
        findNavController().navigate(R.id.action_proposalConclusionFragment_to_homeFragment)
    }
}
