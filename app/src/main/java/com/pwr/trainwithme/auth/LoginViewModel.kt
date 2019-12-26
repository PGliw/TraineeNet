package com.pwr.trainwithme.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwr.trainwithme.R
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    companion object {
        const val TAG = "LoginVM"
        const val MIN_PASSWORD_LENGTH = 5
    }

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login() = viewModelScope.launch {
        // TODO implement
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
        Log.d(TAG, "$username $password ${_loginForm.value}")
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