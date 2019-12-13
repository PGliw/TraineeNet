package com.pwr.trainwithme


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_date_choice.*

/**
 * A simple [Fragment] subclass.
 */
class DateChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, timeslots)
        list_date_choice_fragment_dates.adapter = adapter

        button_date_choice_fragment_back.setOnClickListener {
            findNavController().navigateUp()
        }
        button_date_choice_fragment_next.setOnClickListener {
            findNavController().navigate(R.id.action_dateChoiceFragment_to_centreChoiceFragment)
        }
    }

    val timeslots =  arrayOf("12:00 - 13:30", "13:30 - 15:00", "15:00 - 16:30")

}
