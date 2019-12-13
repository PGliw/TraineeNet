package com.pwr.trainwithme


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pwr.trainwithme.adapters.DetailableAdapter
import kotlinx.android.synthetic.main.fragment_search_results.*

/**
 * A simple [Fragment] subclass.
 */
class SearchResultsFragment : Fragment(), DetailableAdapter.OnItemSelectedListener {

    private val trainers = MockData.trainersDetails
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
        offer_recycler.adapter = DetailableAdapter(requireContext(), trainers, this, DetailableAdapter.ImageType.CIRCLE)
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

    override fun onItemSelected(summary: Summarisable) {
        findNavController().navigate(R.id.action_searchFragment_to_appointmentFragment)
    }
}
