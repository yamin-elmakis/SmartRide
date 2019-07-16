package com.example.smartride.data

import com.example.smartride.utils.FirebaseUtils
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        // Update Token
        FirebaseUtils.updateUserFCMToken()
    }

}