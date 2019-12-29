package com.pwr.trainwithme

import android.app.Application
import com.pwr.commonplatform.data.DataSource
import com.pwr.commonplatform.data.TrainingNetAPI

class TrainingNetApplication : Application() {
    val dataSource by lazy { DataSource(TrainingNetAPI(this)) }
}