package com.pwr.trainwithme.main.centre_location


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.commonplatform.SampleSharedClass
import com.pwr.commonplatform.utils.snack
import com.pwr.trainwithme.R

/**
 * A simple [Fragment] subclass.
 */
class LocationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snack(SampleSharedClass().helloShared())
    }


}
