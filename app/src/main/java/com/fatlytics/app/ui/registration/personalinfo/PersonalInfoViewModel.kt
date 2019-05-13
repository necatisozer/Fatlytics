package com.fatlytics.app.ui.registration.personalinfo

import androidx.lifecycle.LiveData
import com.fatlytics.app.data.source.firebase.UsernameAlreadyTakenException
import com.fatlytics.app.domain.entity.PersonalInfo
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PersonalInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val logger: Logger
) : BaseViewModel() {
    private val mUsernameAlreadyTakenEvent = SingleLiveEvent<Void>()
    val usernameAlreadyTakenEvent: LiveData<Void> get() = mUsernameAlreadyTakenEvent

    private val mNavigateToHealthInfoEvent = SingleLiveEvent<Void>()
    val navigateToHealthInfoEvent: LiveData<Void> get() = mNavigateToHealthInfoEvent

    init {
    }

    fun onPersonalInfoInput(personalInfo: PersonalInfo) {
        userRepository.validatePersonalInfo(personalInfo).doInBackground().subscribeBy(
            onComplete = { mNavigateToHealthInfoEvent.call() },
            onError = {
                when (it) {
                    is UsernameAlreadyTakenException -> mUsernameAlreadyTakenEvent.call()
                    else -> mUnexpectedErrorEvent.call()
                }
            }
        ).also { disposables += it }
    }
}