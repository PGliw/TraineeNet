package com.pwr.trainwithme.training_proposal

import android.app.Application
import android.util.Log
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
    var trainerID: Long? = null
    var sportID: String? = null
    var centreID: String? = null

    private val dataSource = (application as TrainingNetApplication).dataSource

    // TODO timeSlots
    private val trainers = dataSource.load {
        dataSource.trainingNetAPI.getTrainers()
    }

    // trainers overviews
    val trainersOverviews = dataSource.load {
        Log.d(TAG, "Loading trainers overviews")
        dataSource.trainingNetAPI.getTrainersOverviews()
    }

    // trainers cards
    val trainersSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getTrainersSummaries()
    }

    // sports cards
    val sportsSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getSportsSummaries()
    }

    // centres cards
    val centresSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getCentresSummaries()
    }
}