package com.pwr.trainwithme


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search_results.*

/**
 * A simple [Fragment] subclass.
 */
class SearchResultsFragment : Fragment(), OnOfferSelectedListener {

    var sortByIndex = 0
    var filterIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offer_recycler.layoutManager = LinearLayoutManager(requireContext())
        offer_recycler.adapter = OfferAdapter(requireContext(), trainers, this)
        offer_recycler.addOnScrollListener(VerticalScrollHider(button_sort, button_filter))

        button_sort.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.sort)
                .setSingleChoiceItems(R.array.sort_options, sortByIndex)
                { dialog, which ->
                    sortByIndex = which
                    dialog.dismiss()
                }
                .create()
            builder.show()
        }
        button_filter.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.filter)
                .setSingleChoiceItems(R.array.filter_options, filterIndex){
                    dialog, which ->
                    filterIndex = which
                    dialog.dismiss()
                }
            builder.show()
        }
    }

    override fun onOfferSelected(summary: Summarisable) {
        findNavController().navigate(R.id.action_searchFragment_to_appointmentFragment)
    }

    val trainers = arrayOf<Offer>(
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
        ),
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
        ),
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

}
