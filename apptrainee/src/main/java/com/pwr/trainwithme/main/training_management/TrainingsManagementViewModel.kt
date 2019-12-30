package com.pwr.trainwithme.main.training_management

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.data.model.TrainingSummary
import com.pwr.trainwithme.TrainingNetApplication

class TrainingsManagementViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = (application as TrainingNetApplication).dataSource

    private val trainingsSummariesReloadTrigger = MutableLiveData<Boolean>()

    val trainingsSummaries: LiveData<Result<List<TrainingSummary>>> =
        trainingsSummariesReloadTrigger.switchMap {
            dataSource.load {
                dataSource.trainingNetAPI.getTrainingsSummmaries()
            }
        }

    fun refreshTrainingsSummaries() {
        trainingsSummariesReloadTrigger.value = true
    }
}