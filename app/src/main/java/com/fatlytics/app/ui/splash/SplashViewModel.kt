package com.fatlytics.app.ui.splash

import androidx.lifecycle.LiveData
import com.fatlytics.app.data.repository.UserBannedException
import com.fatlytics.app.data.source.firebase.UserNotFoundException
import com.fatlytics.app.data.source.firebase.UserNotSignedInException
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val logger: Logger
) : BaseViewModel() {
    private val mSignedInEvent = SingleLiveEvent<Void>()
    val signedInEvent: LiveData<Void> get() = mSignedInEvent

    private val mFirebaseAuthEvent = SingleLiveEvent<Void>()
    val firebaseAuthEvent: LiveData<Void> get() = mFirebaseAuthEvent

    private val mRegistrationEvent = SingleLiveEvent<Void>()
    val registrationEvent: LiveData<Void> get() = mRegistrationEvent

    private val mBannedEvent = SingleLiveEvent<Void>()
    val bannedEvent: LiveData<Void> get() = mBannedEvent

    init {
        checkUserAuth()
    }

    fun checkUserAuth() {
        userRepository.checkUserAuth().doInBackground().subscribeBy(
            onComplete = { mSignedInEvent.call() },
            onError = {
                when (it) {
                    is UserNotSignedInException -> mFirebaseAuthEvent.call()
                    is UserNotFoundException -> mRegistrationEvent.call()
                    is UserBannedException -> mBannedEvent.call()
                    else -> mUnexpectedErrorEvent.call()
                }
            }
        ).also { disposable += it }
    }
}