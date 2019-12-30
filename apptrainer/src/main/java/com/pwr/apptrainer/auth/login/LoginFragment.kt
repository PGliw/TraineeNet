package com.pwr.apptrainer.auth.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.pwr.apptrainer.R
import com.pwr.commonplatform.data.Result
import com.pwr.commonplatform.utils.afterTextChanged
import com.pwr.commonplatform.utils.snack
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private val loginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update error messages and button state based on login form state
        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer
            button_login_fragment_sign_in.isEnabled = loginState.isDataValid

            text_input_layout_login_fragment_email.error = when (loginState.usernameError) {
                null -> null
                else -> getString(loginState.usernameError)
            }

            text_input_layout_login_fragment_password.error = when (loginState.passwordError) {
                null -> null
                else -> getString(loginState.passwordError)
            }
        })

        // Validate email and password after email change
        text_input_edit_text_login_fragment_email.afterTextChanged {
            validateInputs()
        }

        // Validate email and password after password change
        text_input_edit_text_login_fragment_password.afterTextChanged {
            validateInputs()
        }

        // Sign in on button click
        button_login_fragment_sign_in.setOnClickListener {
            signIn()
        }

        // Navigate to register fragment on text click
        text_login_fragment_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateInputs() {
        loginViewModel.loginDataChanged(
            username = text_input_edit_text_login_fragment_email.text.toString(),
            password = text_input_edit_text_login_fragment_password.text.toString()
        )
    }

    private fun signIn(){
        val loginLiveData = loginViewModel.login(
            username = text_input_edit_text_login_fragment_email.text.toString(),
            password = text_input_edit_text_login_fragment_password.text.toString()
        )
        loginLiveData.observe(viewLifecycleOwner){
            Log.d(TAG, it.toString())
            when(it.status){
                Result.Status.LOADING -> button_login_fragment_sign_in.isEnabled = false
                Result.Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    button_login_fragment_sign_in.isEnabled = true
                }
                else -> {
                    snack(it.message ?: getString(R.string.login_error))
                    button_login_fragment_sign_in.isEnabled = true
                }
            }
        }
    }

}


