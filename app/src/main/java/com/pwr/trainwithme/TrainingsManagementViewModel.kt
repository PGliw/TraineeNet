package com.pwr.trainwithme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pwr.trainwithme.TrainingNetApplication

class TrainingsManagementViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = (application as TrainingNetApplication).dataSource

    val trainingsSummaries = dataSource.load {
        dataSource.trainingNetAPI.getTrainingsSummmaries()
    }
}