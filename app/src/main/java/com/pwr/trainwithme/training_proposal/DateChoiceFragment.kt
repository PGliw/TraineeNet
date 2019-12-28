package com.pwr.trainwithme.training_proposal


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.pwr.trainwithme.data.MockData
import com.pwr.trainwithme.R
import com.pwr.trainwithme.utils.showUpcomingDatePickerDialog
import com.pwr.trainwithme.utils.snack
import kotlinx.android.synthetic.main.fragment_date_choice.*
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DateChoiceFragment : Fragment(), AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    private val proposalViewModel: TrainingProposalViewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, proposalViewModel.timeSlots)
        list_date_choice_fragment_dates.adapter = adapter
        list_date_choice_fragment_dates.onItemClickListener = this

        button_date_choice_fragment_prev_day.setOnClickListener {
            calendar.time = proposalViewModel.day
            calendar.add(Calendar.DATE, -1)
            proposalViewModel.day = calendar.time
        }

        button_date_choice_fragment_next_day.setOnClickListener {
            calendar.time = proposalViewModel.day
            calendar.add(Calendar.DATE, 1)
            proposalViewModel.day = calendar.time
        }

        button_date_choice_fragment_date.setOnClickListener {
            showUpcomingDatePickerDialog(calendar, this)
        }

        proposalViewModel.dayLiveData.observe(viewLifecycleOwner){
            val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
            button_date_choice_fragment_date.text = dateFormat.format(it)
            // enable back button only if currently displayed day is after today's date
            button_date_choice_fragment_prev_day.isEnabled = it.after(Date())
        }

        button_date_choice_fragment_back.setOnClickListener {
            findNavController().navigateUp()
        }

        proposalViewModel.isTimeSlotSet.observe(viewLifecycleOwner){
            button_date_choice_fragment_next.isEnabled = it
        }

        button_date_choice_fragment_next.setOnClickListener {
            findNavController().navigate(R.id.action_dateChoiceFragment_to_centreChoiceFragment)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // TODO replace it with dynamic implementation
        proposalViewModel.setTimeSlot(position)
        findNavController().navigate(R.id.action_dateChoiceFragment_to_centreChoiceFragment)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        proposalViewModel.day = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.time
    }

}
