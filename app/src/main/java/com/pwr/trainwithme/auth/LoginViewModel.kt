package com.pwr.trainwithme.auth

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import com.pwr.trainwithme.R
import com.pwr.trainwithme.TrainingNetApplication
import com.pwr.trainwithme.data.Result
import com.pwr.trainwithme.data.TrainingNetAPI

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "LoginVM"
        const val MIN_PASSWORD_LENGTH = 5
    }

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val ts = (application as TrainingNetApplication).trainingService // TODO refactor casting

    fun login(username: String, password: String) = liveData{
        emit(Result.loading())
        val response = ts.getAuthToken(username, password)
        val body = response.body()
        if (response.isSuccessful && body != null){
            TrainingNetAPI.accessToken = body.accessToken
            TrainingNetAPI.refreshToken = body.refreshToken
            emit(Result.success(null))
        }
        else emit(Result.error(response.message()))
    }


    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            isUserNameValid(username) && isPasswordValid(password) -> LoginFormState(isDataValid = true)
            isUserNameValid(username) -> LoginFormState(passwordError = R.string.invalid_password)
            isPasswordValid(password) -> LoginFormState(usernameError = R.string.invalid_username)
            else -> LoginFormState(
                usernameError = R.string.invalid_username,
                passwordError = R.string.invalid_password
            )
        }
    }

    // Username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // Password validation check
    private fun isPasswordValid(password: String): Boolean = password.length > MIN_PASSWORD_LENGTH


}