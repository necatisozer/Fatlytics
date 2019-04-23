package com.fatlytics.app.ui.registration.healthinfo

import androidx.lifecycle.LiveData
import com.fatlytics.app.domain.entity.HealthInfo
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import javax.inject.Inject

class HealthInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val logger: Logger
) : BaseViewModel() {
    private val mUsernameAlreadyTakenEvent = SingleLiveEvent<Void>()
    val usernameAlreadyTakenEvent: LiveData<Void> get() = mUsernameAlreadyTakenEvent

    init {
    }

    fun onHealthInfoInput(healthInfo: HealthInfo) {
    }
}