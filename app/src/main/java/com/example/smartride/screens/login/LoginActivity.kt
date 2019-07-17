package com.example.smartride.screens.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smartride.R
import com.example.smartride.screens.main.MainActivity
import com.example.smartride.utils.FirebaseUtils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTitle("Login")

        if (FirebaseAuth.getInstance().currentUser != null) {
            startMain()
        }
    }

    fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

//    private var storedVerificationId: String = ""
//
//    companion object {
//        private const val TAG = "LoginActivity"
//        private const val KEY_VERIFICATION_ID = "key_verification_id"
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        outState.putString(KEY_VERIFICATION_ID, storedVerificationId)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        savedInstanceState?.getString(KEY_VERIFICATION_ID)?.let {
//            storedVerificationId = it
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            displayUser(FirebaseAuth.getInstance().currentUser)
//            return // Already Signed In...
//        }
//
//        buttonSubmitPhone.setOnClickListener {
//
//            if (editUsername.text.isEmpty()) {
//                editUsername.error = "One has to have a name..."
//                return@setOnClickListener
//            }
//
//            if (editPhoneNumber.text.isEmpty()) {
//                editPhoneNumber.error = "We wanna stay in touch :["
//                return@setOnClickListener
//            }
//
//            clearErrors()
//
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                editPhoneNumber.text.toString(),
//                60, TimeUnit.SECONDS, this, object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                        Log.d(TAG, "onVerificationCompleted: $credential")
//                        signInWithPhoneAuthCredential(credential)
//                    }
//
//                    override fun onVerificationFailed(exception: FirebaseException?) {
//                        Log.e(TAG, "onVerificationFailed: $exception")
//                        displayError(exception?.message ?: "")
//                    }
//
//                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                        super.onCodeSent(verificationId, token)
//                        Log.d(TAG, "onCodeSent - verificationId: $verificationId, token: $token")
//
//                        storedVerificationId = verificationId
//                    }
//                }
//            )
//        }
//
//        buttonSubmitPinCode.setOnClickListener {
//
//            if (editPinCode.text.isEmpty()) {
//                editPinCode.error = "We really need that code.."
//                return@setOnClickListener
//            }
//
//            if (storedVerificationId.isEmpty()) {
//                displayError("Forgot to fill the Phone Number??")
//                return@setOnClickListener
//            }
//
//            val credential = PhoneAuthProvider.getCredential(storedVerificationId, editPinCode.text.toString())
//            signInWithPhoneAuthCredential(credential)
//
//            clearErrors()
//        }
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "signInWithCredential:success")
//
//                    val request = UserProfileChangeRequest.Builder()
//                        .setDisplayName(editUsername.text.toString())
//                        //.setPhotoUri() // TODO Add Image
//                        .build()
//                    FirebaseAuth.getInstance().currentUser?.updateProfile(request)
//                    ?.addOnSuccessListener {
//                        displayUser(FirebaseAuth.getInstance().currentUser)
//                    }
//                    ?.addOnFailureListener {
//                        displayError("Oopps.. ${it.message}")
//                    }
//
//                } else {
//                    displayError("Failed To Sign In: ${task.exception?.message}")
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                }
//            }
//    }
//
//    private fun displayUser(user: FirebaseUser?) {
//
//        FirebaseUtils.updateUserFCMToken()
//
//        textUser.visibility = View.VISIBLE
//        groupMain.visibility = View.GONE
//
//        textUser.text = "User Signed In!!!" +
//                "\nName: ${user?.displayName}" +
//                "\nPhone Number: ${user?.phoneNumber}"
//
//        Handler().postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }, 3000)
//    }
//
//    private fun displayError(errorMessage: String) {
//        textError.text = errorMessage
//        textError.visibility = View.VISIBLE
//    }
//
//    private fun clearErrors() {
//        textError.visibility = View.GONE
//        textError.text = ""
//        editUsername.error = null
//        editPhoneNumber.error = null
//        editPinCode.error = null
//    }

}