package com.pwr.trainwithme.auth.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import com.pwr.trainwithme.R
import com.pwr.trainwithme.TrainingNetApplication

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "LoginVM"
        const val MIN_PASSWORD_LENGTH = 5
    }

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val dataSource = (application as TrainingNetApplication).dataSource

    fun login(username: String, password: String) = dataSource.login(username, password)

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            isUserNameValid(username) && isPasswordValid(password) -> LoginFormState(
                isDataValid = true
            )
            isUserNameValid(username) -> LoginFormState(
                passwordError = R.string.invalid_password
            )
            isPasswordValid(password) -> LoginFormState(
                usernameError = R.string.invalid_username
            )
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