package com.pwr.apptrainer.main.proposal_management


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.apptrainer.R

/**
 * A simple [Fragment] subclass.
 */
class ProposalDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal_details, container, false)
    }


}
