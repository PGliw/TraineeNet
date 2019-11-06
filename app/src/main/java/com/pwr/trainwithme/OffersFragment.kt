package com.pwr.trainwithme


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import kotlinx.android.synthetic.main.fragment_offers.*

/**
 * A simple [Fragment] subclass.
 */
class OffersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSet = arrayOf(
            Offer("https://thumbs.dreamstime.com/z/atleta-divertido-44183141.jpg", "Gruby", "Profesjonalny trener"),
            Offer("https://www.wikihow.com/images/thumb/f/f8/Become-a-Personal-Trainer-Step-1.jpg/aid101070-v4-728px-Become-a-Personal-Trainer-Step-1.jpg.webp", "Jan Niezbędny", "fn iaonf niaonf onoifn aon on[")
        )
        card_stack.layoutManager = CardStackLayoutManager(requireContext())
        card_stack.adapter = CardStackAdapter(requireContext(), dataSet)
    }


}
