package com.pwr.trainwithme

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

class TrainingProposalViewModel : ViewModel() {

    // inputs
    var day: Date? = null
    var startDate: Date? = null
    var endDate: Date? = null
    var trainer: Trainer? = null

    // observable live data
//    val trainersSummaries: LiveData<List<Summarisable>>
//        get() = _trainersSummaries
//    val sportsSummaries: LiveData<List<Summarisable>>
//        get() = _sportsSummaries
//    val centresSummaries: LiveData<List<Summarisable>>
//        get() = _centresSummaries

    // TODO timeSlots
    private val trainers = MutableLiveData<List<Trainer>>(MockData.trainers)
    private val sports = MutableLiveData<List<Sport>>(MockData.sports)
    private val centres = MutableLiveData<List<SportCentre>>(MockData.sportCentres)

    // trainers
    val trainersSummaries : LiveData<List<Summarisable>> = Transformations.map(trainers) {
        it.map { trainer -> TrainerVM(trainer) }
    }

    // sports
    val sportsSummaries : LiveData<List<Summarisable>> = Transformations.map(sports){
        it.map { sport -> SportVM(sport) }
    }

    // centres
    val centresSummaries : LiveData<List<Summarisable>> = Transformations.map(centres){
        it.map { sportCentre -> SportCentreVM(sportCentre) }
    }

}