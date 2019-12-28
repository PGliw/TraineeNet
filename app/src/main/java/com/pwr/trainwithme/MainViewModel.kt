package com.pwr.trainwithme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = (application as TrainingNetApplication).dataSource
    val isAuthenticated: LiveData<Boolean> = dataSource.isAuthenticated
}