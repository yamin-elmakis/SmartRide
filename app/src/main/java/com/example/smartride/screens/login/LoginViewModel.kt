package com.example.smartride.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    companion object {
        private val TAG = LoginViewModel::class.java.simpleName
    }

    private var storedVerificationId: String = ""

    val statePhoneVerification = MutableLiveData<State>()

    fun phoneVerificationCompleted() {
//        statePhoneVerification.value =
    }

    fun pinCodeSent(verificationId: String) {
        storedVerificationId = verificationId
        statePhoneVerification.value = LoginViewModel.State(pinCodeSent = true)
    }

    data class State(
        val pinCodeSent: Boolean = false,
        val phoneVerificationComplete: Boolean = false,
        val error: String? = null
    )

}