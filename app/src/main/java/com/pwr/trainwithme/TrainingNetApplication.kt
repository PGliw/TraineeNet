package com.pwr.trainwithme

import android.app.Application
import com.pwr.trainwithme.data.TrainingService

class TrainingNetApplication : Application() {
    val trainingService = TrainingService()
}