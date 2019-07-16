package com.example.smartride.screens.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartride.R
import com.example.smartride.screens.main.MainActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private var storedVerificationId: String = ""

    companion object {
        private const val TAG = "LoginActivity"
        private const val KEY_VERIFICATION_ID = "key_verification_id"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_VERIFICATION_ID, storedVerificationId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.getString(KEY_VERIFICATION_ID)?.let {
            storedVerificationId = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (FirebaseAuth.getInstance().currentUser != null) {
            displayUser(FirebaseAuth.getInstance().currentUser)
            return // Already Signed In...
        }

        buttonSubmitPhone.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                editPhoneNumber.text.toString(),
                60, TimeUnit.SECONDS, this, object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted: $credential")
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(exception: FirebaseException?) {
                        Log.e(TAG, "onVerificationFailed: $exception")
                        Toast.makeText(applicationContext, "Error: ${exception?.message}", Toast.LENGTH_LONG).show()
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(verificationId, token)
                        Log.d(TAG, "onCodeSent - verificationId: $verificationId, token: $token")

                        storedVerificationId = verificationId
                    }
                }
            )
        }

        buttonSubmitPinCode.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, editPinCode.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    displayUser(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun displayUser(user: FirebaseUser?) {
        textUser.text = "User Signed In!!!" +
                "\nName: ${user?.displayName}" +
                "\nPhone Number: ${user?.phoneNumber}"

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

}