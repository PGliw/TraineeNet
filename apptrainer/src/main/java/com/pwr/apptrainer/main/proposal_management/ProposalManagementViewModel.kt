package com.pwr.apptrainer.main.proposal_management

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pwr.apptrainer.TrainingNetApplication

class ProposalManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource = (application as TrainingNetApplication).dataSource
    private val proposalsReloadTrigger = MutableLiveData<Boolean>()

    val trainingProposals = proposalsReloadTrigger.switchMap {
        dataSource.load {
            dataSource.trainingNetAPI.getTrainerTrainingsOverviews(
                trainingStatus = "PROPOSED"
            )
        }
    }

    fun refreshProposals(){
        proposalsReloadTrigger.value = true
    }
}