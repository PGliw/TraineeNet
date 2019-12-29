package com.pwr.trainwithme.training_proposal


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.pwr.trainwithme.R
import com.pwr.trainwithme.adapters.SliderAdapter
import com.pwr.trainwithme.data.OfferResponse
import com.pwr.trainwithme.data.Result
import com.pwr.trainwithme.data.TrainerDetails
import com.pwr.trainwithme.utils.snack
import com.smarteist.autoimageslider.IndicatorAnimations
import kotlinx.android.synthetic.main.fragment_sport_choice.*

/**
 * A simple [Fragment] subclass.
 */
class SportChoiceFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(requireActivity())[TrainingProposalViewModel::class.java]
    }
    private val adapter by lazy {
        SliderAdapter(requireContext(), listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Disable next button until sport is chosen
        button_sport_fragment_next.isEnabled = false

        // init image slider and its adapter
        image_slider_fragment_sport_choice.sliderAdapter = adapter
        image_slider_fragment_sport_choice.setIndicatorAnimation(IndicatorAnimations.DROP)

        // observe to trainer details result
        // if success render the UI, if error disable next button
        viewModel.trainerDetails.observe(viewLifecycleOwner){
            when (it.status) {
                Result.Status.LOADING -> snack(getString(R.string.loading)) // TODO change to progress bar
                Result.Status.SUCCESS -> if(it.data != null) renderUI(it.data) else snack(getString(R.string.null_data_error))
                Result.Status.ERROR -> {
                    button_sport_fragment_next.isEnabled = false
                    snack(it.message ?: getString(R.string.unknown_error))
                }
            }
        }

        renderSportChoiceButton()
        renderPricePerHourText()

        button_sport_fragment_back.setOnClickListener {
            findNavController().navigateUp()
        }

        button_sport_fragment_next.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentFragment_to_dateChoiceFragment)
        }
    }

    /**
     * Setup UI and listeners with given trainerDetails
     */
    private fun renderUI(trainerDetails: TrainerDetails){
        val title = "${trainerDetails.firstName} ${trainerDetails.lastName}, ${trainerDetails.age}"
        text_fragment_sport_choice_trainer_name.text = title
        text_fragment_sport_choice_trainer_description.text = trainerDetails.description
        adapter.imagesUrls = trainerDetails.imagesUrls
        button_sport.setOnClickListener { renderSportChoice(
            trainerDetails.offers
        ) }
        viewModel.sportIdLiveData.observe(viewLifecycleOwner){ sportID ->
            val matchingOffer = trainerDetails.offers.find { it.sportID == sportID }
            val sportName = matchingOffer?.sportName
            val pricePerHour = matchingOffer?.pricePerHour
            renderSportChoiceButton(sportName)
            renderPricePerHourText(pricePerHour)
            // Enable next button only if there is matching offer
            button_sport_fragment_next.isEnabled = matchingOffer != null
        }
    }

    private fun renderSportChoiceButton(text : String? = null){
        button_sport.text = text ?: getString(R.string.choose_sport)
    }

    private fun renderPricePerHourText(price: Float? = null){
        val text = when(price){
            null -> " "
            else -> "%.2f".format(price) + " ${getString(R.string.dollars_per_hour)}"
        }
        text_fragment_sport_choice_cost.text = text
    }

    /**
     * Setup single choice alert dialog with following data from viewModel:
     * * offers list
     * * chosen sport
     * If no sport is chosen 0-item is selected
     */
    private fun renderSportChoice(offers: List<OfferResponse>){
        val sports = offers.map { it.sportName }
        val initialSportID = viewModel.sportID
        val foundPosition = offers.indexOfFirst { it.sportID == initialSportID }
        val initialPosition = if(foundPosition >= 0) foundPosition else 0
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.sport_discipline)
            .setSingleChoiceItems(sports.toTypedArray(), initialPosition) { dialog, which ->
                viewModel.setSportIdAndName(offers[which].sportID, offers[which].sportName)
                dialog.dismiss()
            }
        builder.create().show()
    }
}
