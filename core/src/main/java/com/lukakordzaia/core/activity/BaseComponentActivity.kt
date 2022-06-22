package com.lukakordzaia.core.activity

import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class BaseComponentActivity : ComponentActivity() {
    protected var auth = Firebase.auth
}