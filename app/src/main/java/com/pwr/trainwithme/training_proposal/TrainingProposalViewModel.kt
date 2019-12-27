package com.pwr.trainwithme.training_proposal

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pwr.trainwithme.*
import com.pwr.trainwithme.data.*
import java.util.*
import java.time.LocalDateTime

class TrainingProposalViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val TAG = "TrainingProposalVM"
    }

    // authentication status - TODO handle it in repository / data source
    private val _isAuthenticated = MutableLiveData<Boolean>(true)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    // inputs
    val day = MutableLiveData<Date>(Date())
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null
    var trainerID: String? = null
    var sportID: String? = null
    var centreID: String? = null

    fun searchOffers() = liveData {
        emit(Result.loading(null))
        val trainersResponse = ts.getTrainers()
        if(trainersResponse.isSuccessful && trainersResponse.body() != null){
        }
        emit(Result.success(null))
    }

    private val ts = (application as TrainingNetApplication).trainingService // TODO refactor casting

    // TODO timeSlots
    private val trainers = liveData {
        val trainersRes = ts.getTrainers()
        if(trainersRes.isSuccessful) emit(trainersRes.body())
        else {
            val code = trainersRes.code()
            if(code == 401) _isAuthenticated.value = false
            emit(listOf())
        } // TODO handle errors
    }

    private val sports = liveData {
        emit(MockData.sports)
    }
    private val centres = liveData {
        emit(MockData.sportCentres)
    }

    // trainers
    val trainersSummaries = liveData {
        emit(Result.loading(null))
        val trainersRes = ts.getTrainersSummaries()
        Log.d(TAG, trainersRes.toString())
        if(trainersRes.isSuccessful) {
            val body = trainersRes.body()
            Log.d(TAG, body.toString())
            emit(Result.success(body as List<Summarisable>))
        }
        else {
            val code = trainersRes.code()
            if(code == 401) _isAuthenticated.value = false
            emit(Result.error("Some error"))
        }
    }

    // sports
    val sportsSummaries = liveData {
        emit(Result.loading(null))
        val sportsRes = ts.getSportsSummaries()
        Log.d(TAG, sportsRes.toString())
        if(sportsRes.isSuccessful) {
            val body = sportsRes.body()
            Log.d(TAG, body.toString())
            emit(Result.success(body as List<Summarisable>))
        }
        else {
            val code = sportsRes.code()
            if(code == 401) _isAuthenticated.value = false
            emit(Result.error("Some error"))
        }
    }

    // centres
    val centresSummaries = liveData{
        emit(Result.loading(null))
        val centresRes = ts.getCentresSummaries()
        Log.d(TAG, centresRes.toString())
        if(centresRes.isSuccessful) {
            val body = centresRes.body()
            Log.d(TAG, body.toString())
            emit(Result.success(centresRes.body() as List<Summarisable>))
        }
        else {
            val code = centresRes.code()
            if(code == 401) _isAuthenticated.value = false
            emit(Result.error("Some error"))
        }
    }
}