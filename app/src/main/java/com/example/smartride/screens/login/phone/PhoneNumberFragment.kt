package com.example.smartride.screens.login.phone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.screens.login.LoginActivity
import com.example.smartride.screens.login.LoginViewModel
import com.example.smartride.screens.main.MainActivity
import com.example.smartride.utils.FirebaseUtils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_enter_phone.*
import java.util.concurrent.TimeUnit

class PhoneNumberFragment : Fragment() {

    private lateinit var navigation: NavController
    private lateinit var viewModel: LoginViewModel

    companion object {
        private val TAG = PhoneNumberFragment::class.java.simpleName
        private const val USER_NAME = "Joe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigation = NavHostFragment.findNavController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_enter_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textUser.text = getString(R.string.hi_user, USER_NAME)

        buttonSubmit.setOnClickListener {
            if (editPhone.text.isEmpty()) {
                inputPhone.error = "Missing Field"
                return@setOnClickListener
            }

            val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as (InputMethodManager)
            imm.hideSoftInputFromWindow(editPhone.windowToken, 0);

            verifyPhone(editPhone.text.trim().toString())
//            viewModel.verifyPhone(editPhone.text.trim().toString())
        }
    }

    private fun verifyPhone(phoneNumber: String) {

        progress.visibility = View.VISIBLE

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60, TimeUnit.SECONDS, activity!!, object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted: $credential")
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException?) {
                    Log.e(TAG, "onVerificationFailed: $exception")
                    inputPhone.error = "Verification Failed"

                    progress.visibility = View.INVISIBLE
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    Log.d(TAG, "onCodeSent - verificationId: $verificationId, token: $token")
//                    viewModel.pinCodeSent(verificationId)
                    val credential = PhoneAuthProvider.getCredential(verificationId, "123456")
                    signInWithPhoneAuthCredential(credential)
                }
            }
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val request = UserProfileChangeRequest.Builder()
                        .setDisplayName(USER_NAME)
                        //.setPhotoUri() // TODO Add Image
                        .build()
                    FirebaseAuth.getInstance().currentUser?.updateProfile(request)
                    ?.addOnSuccessListener {
//                        displayUser(FirebaseAuth.getInstance().currentUser)
                        FirebaseUtils.updateUserFCMToken()
                        (activity as? LoginActivity)?.let {
                            it.startMain()
                        }
                    }
                    ?.addOnFailureListener {
//                        displayError("Oopps.. ${it.message}")
                        inputPhone.error = "Something is wrong..."

                        progress.visibility = View.INVISIBLE
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    inputPhone.error = "Sign In Failed: ${task.exception ?: ""}"

                    progress.visibility = View.INVISIBLE
                }
            }
    }

}