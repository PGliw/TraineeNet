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
class ProposalRefusalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposal_refusal, container, false)

    }


}
