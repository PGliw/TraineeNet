package com.pwr.trainwithme


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.SummaryAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), SummaryAdapter.OnSummarySelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcoming_trainings_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcoming_trainings_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                trainings,
                this,
                cardType = SummaryAdapter.MEDIUM
            )

        passes_recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        passes_recycler.adapter =
            SummaryAdapter(
                requireContext(),
                passes,
                this,
                SummaryAdapter.WIDE
            )
    }

    override fun onSummarySelected(summary: Summarisable) {
        when (summary) {
            is TrainingFacade -> findNavController().navigate(R.id.action_searchFragment_to_appointmentFragment)
            else -> {
            } // TODO add pass details fragment and navigation
        }
    }

    val trainings = arrayOf<Summarisable>(
        TrainingFacade(
            Training(
                Date(), 1.5f, 10, Sport(
                    "Tennis",
                    "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                )
            )
        ),
        TrainingFacade(
            Training(
                Date(), 2.0f, 10, Sport(
                    "Tennis",
                    "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                )
            )
        )
    )

    val passes = arrayOf<Summarisable>(
        PassFacade(
            Pass(
                "MultiSport",
                "https://fitness-legionowo.pl/wp-content/uploads/2017/06/MultiSportPlus.jpg"
            )
        ),
        PassFacade(
            Pass(
                "FitProfit",
                "http://www.pcs-belchatow.pl/media/k2/items/cache/e9b0f857826ab994b1b1904c051bbf59_XL.jpg"
            )
        )
    )

}
