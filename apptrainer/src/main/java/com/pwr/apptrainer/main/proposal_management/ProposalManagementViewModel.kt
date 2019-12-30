package com.pwr.apptrainer.main.proposal_management

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pwr.apptrainer.TrainingNetApplication

class ProposalManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource = (application as TrainingNetApplication).dataSource
    private val proposalsReloadTrigger = MutableLiveData<Boolean>()
    var proposalID: Long? = null
    set(value) {
        proposalIdLiveData.value = value
        field = value
    }
    private val proposalIdLiveData = MutableLiveData<Long>()

    val trainingProposals = proposalsReloadTrigger.switchMap {
        dataSource.load {
            dataSource.trainingNetAPI.getTrainerTrainingsOverviews(
                trainingStatus = "PROPOSED"
            )
        }
    }

    val proposalDetails = proposalIdLiveData.switchMap {
        dataSource.load {
            dataSource.trainingNetAPI.getTrainerTrainingDetails(it)
        }
    }

    fun refreshProposals(){
        proposalsReloadTrigger.value = true
    }
}