package com.pwr.trainwithme


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.DetailableAdapter
import kotlinx.android.synthetic.main.fragment_centre_choice.*

/**
 * A simple [Fragment] subclass.
 */
class CentreChoiceFragment : Fragment(), DetailableAdapter.OnItemSelectedListener {

    companion object{
        const val TAG = "CentreChoiceFragment"
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
        recycler_centre_choice_fragment_centres.layoutManager = LinearLayoutManager(requireContext())
        recycler_centre_choice_fragment_centres.adapter = DetailableAdapter(
            requireContext(), MockData.centresDetails, this
        )
    }

    override fun onItemSelected(summary: Summarisable) {
        Log.i(TAG, "onItemSelected()")
    }


}
