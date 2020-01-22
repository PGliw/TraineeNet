package com.pwr.apptrainer.main.proposal_management

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pwr.apptrainer.TrainingNetApplication
import com.pwr.commonplatform.data.model.trainer.TrainingStatusDTO

class ProposalManagementViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "PMVM"
    }

    private val dataSource = (application as TrainingNetApplication).dataSource
    private val proposalsReloadTrigger = MutableLiveData<Boolean>()
    var proposalID: Long? = null
        set(value) {
            proposalIdLiveData.value = value
            field = value
        }
    private val proposalIdLiveData = MutableLiveData<Long>()
    var refusalMessage: String? = null

    var sortOptionPosition: Int = 0

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

    fun acceptProposal() = dataSource.load {
        Log.d(TAG, "acceptProposal()")
        dataSource.trainingNetAPI.updateTrainerTrainingStatus(
            proposalID ?: throw NullPointerException("proposalID == null"),
            TrainingStatusDTO("ACCEPTED")
        )
    }

    fun denyProposal() = dataSource.load {
        dataSource.trainingNetAPI.updateTrainerTrainingStatus(
            proposalID ?: throw NullPointerException("proposalID == null"),
            TrainingStatusDTO("DENIED")
        )
    }

    fun refreshProposals() {
        proposalsReloadTrigger.value = true
    }
}