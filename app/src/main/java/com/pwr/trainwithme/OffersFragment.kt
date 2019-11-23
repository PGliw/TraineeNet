package com.pwr.trainwithme


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.fragment_offers.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class OffersFragment : Fragment(), OnSummarySelectedListener, DatePickerDialog.OnDateSetListener {

    val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sports_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sports_recycler.adapter =
            SummaryAdapter(requireContext(), sports, this, SummaryAdapter.MEDIUM)
        trainers_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trainers_recycler.adapter = SummaryAdapter(requireContext(), trainers, this)
        objects_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        objects_recycler.adapter =
            SummaryAdapter(requireContext(), sportObjects, this, SummaryAdapter.MEDIUM)

        edit_text_date.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this, // listener to data set
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH] // Initially set current date TODO show date from viewModel
            ).show() // show dialog
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSummarySelected(summary: Summarisable) {
        findNavController().navigate(R.id.action_offersFragment_to_searchFragment)
    }

    val sports = arrayOf<Summarisable>(
        Sport(
            "Tennis",
            "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        ),
        Sport(
            "Gym",
            "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        ),
        Sport(
            "Box",
            "https://images.pexels.com/photos/163403/box-sport-men-training-163403.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        )
    )
    val trainers = arrayOf<Summarisable>(
        Trainer(
            "John",
            "Doe",
            "https://images.pexels.com/photos/1546139/pexels-photo-1546139.jpeg?cs=srgb&dl=chlopak-czas-wolny-deska-surfingowa-dorosly-1546139.jpg&fm=jpg",
            "Surfing instructor",
            20.0f
        ),
        Trainer(
            "Bob",
            "Smith",
            "https://images.pexels.com/photos/733500/pexels-photo-733500.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
            "Trainer",
            30.0f
        )
    )
    val sportObjects = arrayOf<Summarisable>(
        SportObject(
            "Surfpoint",
            "https://www.surfpoint.pl/wp-content/uploads/2015/04/team-biegnie-compressor.jpg"
        ),
        SportObject("Fitness Academy", "https://i.ytimg.com/vi/0-f1meMXtCI/maxresdefault.jpg")
    )

}
