package com.lukakordzaia.subscriptionmanager.network.networkmodels

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

data class UserLoginRequestNetwork(
    val auth: FirebaseAuth,
    val credential: AuthCredential
)
