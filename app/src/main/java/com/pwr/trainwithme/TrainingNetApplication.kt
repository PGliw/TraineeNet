package com.pwr.trainwithme

import android.app.Application
import com.pwr.trainwithme.data.DataSource
import com.pwr.trainwithme.data.TrainingNetAPI

class TrainingNetApplication : Application() {
    val dataSource by lazy { DataSource(TrainingNetAPI(this)) }
}