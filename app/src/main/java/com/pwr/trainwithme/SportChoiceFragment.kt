package com.pwr.trainwithme


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.pwr.trainwithme.adapters.SliderAdapter
import com.pwr.trainwithme.data.MockData
import com.smarteist.autoimageslider.IndicatorAnimations
import kotlinx.android.synthetic.main.fragment_sport_choice.*

/**
 * A simple [Fragment] subclass.
 */
class SportChoiceFragment : Fragment() {

    private val sports = MockData.sportNames
    private val imagesUrls = MockData.imagesUrls
    var sportIndex = 0
    var timeOptionIndex = 0
    var locationIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSlider.sliderAdapter = SliderAdapter(requireContext(), imagesUrls)
        imageSlider.setIndicatorAnimation(IndicatorAnimations.DROP)

        button_sport.setOnClickListener {
            renderSportChoice()
        }

        button_sport_fragment_next.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentFragment_to_dateChoiceFragment)
        }
    }

    private fun renderSportChoice(){
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.sport_discipline)
            .setSingleChoiceItems(sports.toTypedArray(), sportIndex) { dialog, which ->
                sportIndex = which
                dialog.dismiss()
            }
        builder.create().show()
    }
}
