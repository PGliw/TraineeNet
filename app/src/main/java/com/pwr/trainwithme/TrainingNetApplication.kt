package com.pwr.trainwithme

import android.app.Application
import com.pwr.trainwithme.data.TrainingNetAPI

class TrainingNetApplication : Application() {
    val trainingService = TrainingNetAPI()
}