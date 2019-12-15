package com.pwr.trainwithme

import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.coroutineContext

class TrainingProposalViewModel : ViewModel() {

    // inputs
    val day = MutableLiveData<Date>(Date())
    var startDate: Date? = null
    var endDate: Date? = null
    var trainerID: String? = null
    var sportID: String? = null
    var centreID: String? = null


    // TODO timeSlots
    private val trainers = liveData {
        emit(MockData.trainers)
    }
    private val sports = liveData {
        emit(MockData.sports)
    }
    private val centres = liveData {
        emit(MockData.sportCentres)
    }

    // trainers
    val trainersSummaries: LiveData<List<Summarisable>> = Transformations.map(trainers) {
        it.map { trainer -> TrainerVM(trainer) }
    }

    // sports
    val sportsSummaries: LiveData<List<Summarisable>> = Transformations.map(sports) {
        it.map { sport -> SportVM(sport) }
    }

    // centres
    val centresSummaries: LiveData<List<Summarisable>> = Transformations.map(centres) {
        it.map { sportCentre -> SportCentreVM(sportCentre) }
    }

    fun getSports(locationID: String?){}
    fun getTrainers(locationID: String?){}
    fun getCentres(locationID: String?){}
    fun postTraining(trainingDTO : String){} // TODO request

}