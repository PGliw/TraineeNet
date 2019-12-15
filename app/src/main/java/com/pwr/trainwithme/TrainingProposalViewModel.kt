package com.pwr.trainwithme

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pwr.trainwithme.data.TrainingService
import java.util.*

class TrainingProposalViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val TAG = "TrainingProposalVM"
    }

    // inputs
    val day = MutableLiveData<Date>(Date())
    var startDate: Date? = null
    var endDate: Date? = null
    var trainerID: String? = null
    var sportID: String? = null
    var centreID: String? = null

    private val ts = (application as TrainingNetApplication).trainingService // TODO refactor casting

    // TODO timeSlots
    private val trainers = liveData {
        val trainersRes = ts.getTrainers()
        if(trainersRes.isSuccessful) emit(trainersRes.body())
        else emit(listOf()) // TODO handle errors
    }
    private val sports = liveData {
        emit(MockData.sports)
    }
    private val centres = liveData {
        emit(MockData.sportCentres)
    }

    // trainers
    val trainersSummaries: LiveData<List<Summarisable>> = Transformations.map(trainers) {
        it?.map { trainer -> TrainerVM(trainer) } ?: listOf()
    }

    // sports
    val sportsSummaries: LiveData<List<Summarisable>> = Transformations.map(sports) {
        it.map { sport -> SportVM(sport) }
    }

    // centres
    val centresSummaries: LiveData<List<Summarisable>> = Transformations.map(centres) {
        it.map { sportCentre -> SportCentreVM(sportCentre) }
    }
}