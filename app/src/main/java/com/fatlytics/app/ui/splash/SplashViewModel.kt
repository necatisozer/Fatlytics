package com.fatlytics.app.ui.splash

import androidx.lifecycle.LiveData
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {
    private val mSignedInEvent = SingleLiveEvent<Void>()
    val signedInEvent: LiveData<Void>
        get() = mSignedInEvent

    private val mSignInEvent = SingleLiveEvent<Void>()
    val signInEvent: LiveData<Void>
        get() = mSignInEvent

    init {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            mSignedInEvent.call()
        } else {
            // not signed in
            mSignInEvent.call()
        }
    }
}