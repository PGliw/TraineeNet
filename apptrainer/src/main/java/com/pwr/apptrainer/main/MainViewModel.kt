package com.pwr.apptrainer.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pwr.apptrainer.TrainingNetApplication

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = (application as TrainingNetApplication).dataSource
    val isAuthenticated: LiveData<Boolean> = dataSource.isAuthenticated
}