package com.pwr.trainwithme.training_proposal

import android.app.Application
import androidx.lifecycle.*
import com.pwr.trainwithme.TrainingNetApplication
import com.pwr.trainwithme.data.Summarisable
import java.time.LocalDateTime
import java.util.*

class TrainingProposalViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "TrainingProposalVM"
    }

    // inputs
    val day = MutableLiveData<Date>(Date())
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null

    /**
     *  setting trainerID != null causes fetching trainerDetails if they (trainingDetails) are observed
     */
    var trainerID: Long? = null
        set(value) {
            if (value != null) trainerIdLiveData.value = value
            field = value
        }
    private val trainerIdLiveData = MutableLiveData<Long>()

    /**
     *  setting trainerID != null causes changing sportLiveData which causes changes to UI
     */
    var sportID: Long? = null
        set(value) {
            if (value != null) _sportIdLiveData.value = value
            field = value
        }
    private val _sportIdLiveData = MutableLiveData<Long>()
    val sportIdLiveData: LiveData<Long> = _sportIdLiveData

    var centreID: String? = null

    private val dataSource = (application as TrainingNetApplication).dataSource


    val trainerDetails = trainerIdLiveData.switchMap {
        liveData {
            emitSource(dataSource.load {
                dataSource.trainingNetAPI.getTrainerDetails(it)
            })
        }
    }

    // trainers overviews
    val trainersOverviews = dataSource.load {
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