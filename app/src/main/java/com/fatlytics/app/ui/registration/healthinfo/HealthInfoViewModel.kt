package com.fatlytics.app.ui.registration.healthinfo

import androidx.lifecycle.LiveData
import com.fatlytics.app.domain.entity.HealthInfo
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HealthInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val mSignedInEvent = SingleLiveEvent<Void>()
    val signedInEvent: LiveData<Void> get() = mSignedInEvent

    init {
    }

    fun onHealthInfoInput(healthInfo: HealthInfo) {
        userRepository.completeRegistration(healthInfo)
            .doInBackground()
            .subscribeBy(
                onComplete = { mSignedInEvent.call() },
                onError = { mUnexpectedErrorEvent.call() }
            )
            .also { disposables += it }
    }
}