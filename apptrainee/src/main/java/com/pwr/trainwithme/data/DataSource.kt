package com.pwr.trainwithme.data

import androidx.lifecycle.*
import retrofit2.Response
import java.lang.Exception

class DataSource(val trainingNetAPI: TrainingNetAPI) {
    companion object {
        const val UNAUTHORIZED_ERROR_CODE = 401
    }

    private val _isAuthenticated = MutableLiveData<Boolean>(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    fun <T> load(
        onSuccess: ((T) -> Unit)? = null,
        call: suspend () -> Response<out T>
    ) = liveData {
        emit(Result.loading())
        try {
            val res = call()
            if (res.isSuccessful) {
                val resBody = res.body() ?: throw TypeCastException()
                onSuccess?.invoke(resBody)
                emit(Result.success(resBody))
            } else {
                if (res.code() == UNAUTHORIZED_ERROR_CODE) _isAuthenticated.postValue(false)
                emit(Result.error(res.message()))
            }
        } catch (e: Exception) {
            emit(Result.error(e.message ?: "Unknown message"))
        }
    }

    fun login(email: String, password: String) = load(
        {
            TrainingNetAPI.accessToken = it.accessToken
            TrainingNetAPI.refreshToken = it.refreshToken
            _isAuthenticated.postValue(true)
        }
    ) {
        trainingNetAPI.getAuthToken(email, password)
    }
}