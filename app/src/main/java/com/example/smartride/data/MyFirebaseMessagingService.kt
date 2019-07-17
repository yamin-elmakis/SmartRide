package com.example.smartride.data

import android.util.Log
import com.example.smartride.utils.FirebaseUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MessageService"
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)

        Log.d(TAG, "Message: $message")
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        // Update Token
        FirebaseUtils.updateUserFCMToken()
    }

}