package com.fatlytics.app.ui.splash

import androidx.lifecycle.LiveData
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {
    private val _signedInEvent = SingleLiveEvent<Void>()
    val signedInEvent: LiveData<Void>
        get() = _signedInEvent

    private val _signInEvent = SingleLiveEvent<Void>()
    val signInEvent: LiveData<Void>
        get() = _signInEvent

    init {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            _signedInEvent.call()
        } else {
            // not signed in
            _signInEvent.call()
        }
    }
}