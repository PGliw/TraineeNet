package com.pwr.trainwithme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap

class TrainingsManagementViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = (application as TrainingNetApplication).dataSource

    private val trainingsSummariesReloadTrigger = MutableLiveData<Boolean>()

    val trainingsSummaries = trainingsSummariesReloadTrigger.switchMap {
        dataSource.load {
            dataSource.trainingNetAPI.getTrainingsSummmaries()
        }
    }

    fun refreshTrainingsSummaries() {
        trainingsSummariesReloadTrigger.value = true
    }
}