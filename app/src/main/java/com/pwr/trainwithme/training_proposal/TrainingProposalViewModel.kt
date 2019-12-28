package com.pwr.trainwithme.training_proposal

import android.app.Application
import androidx.lifecycle.*
import com.pwr.trainwithme.*
import com.pwr.trainwithme.data.*
import java.util.*
import java.time.LocalDateTime

class TrainingProposalViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val TAG = "TrainingProposalVM"
    }


    // inputs
    val day = MutableLiveData<Date>(Date())
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null
    var trainerID: String? = null
    var sportID: String? = null
    var centreID: String? = null

    private val dataSource = (application as TrainingNetApplication).dataSource

    // TODO timeSlots
    private val trainers = dataSource.load {
        dataSource.trainingNetAPI.getTrainers()
    }

    private val sports = liveData {
        emit(MockData.sports)
    }
    private val centres = liveData {
        emit(MockData.sportCentres)
    }

    // trainers
    val trainersSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getTrainersSummaries()
    }

    // sports
    val sportsSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getSportsSummaries()
    }

    // centres
    val centresSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getCentresSummaries()
    }
}