package com.example.smartride.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

object FirebaseUtils {

    private const val TAG = "FirebaseUtils"

    object DBUsers {
        const val TABLE_NAME = "users/"
        const val FIELD_FCM_TOKEN = "fcmToken"
    }

    fun updateUserFCMToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceResult ->
            FirebaseAuth.getInstance().currentUser?.let { user ->
                val usersReference = FirebaseDatabase.getInstance().getReference(DBUsers.TABLE_NAME + user.uid)
                usersReference.child(DBUsers.FIELD_FCM_TOKEN).setValue(instanceResult.token)
            }?.addOnSuccessListener {
                Log.d(TAG, "updateUserFCMToken SUCCESS")
            }?.addOnFailureListener {
                Log.e(TAG, "updateUserFCMToken error: ${it.message}")
            }
        }
    }

}