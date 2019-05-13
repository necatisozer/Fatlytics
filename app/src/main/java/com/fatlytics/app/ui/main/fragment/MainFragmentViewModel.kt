package com.fatlytics.app.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val logger: Logger
) : BaseViewModel() {
    private val mUserInfo = MutableLiveData<String>()
    val userInfo: LiveData<String> get() = mUserInfo

    private val mSignOutEvent = SingleLiveEvent<Void>()
    val signOutEvent: LiveData<Void> get() = mSignOutEvent

    init {
        val user = FirebaseAuth.getInstance().currentUser

        val info = """
            |Display name: ${user?.displayName}
            |Email: ${user?.email}
            |Phone number: ${user?.phoneNumber}
            |UID: ${user?.uid}
            """.trimMargin()

        mUserInfo.value = info
    }

    fun onSignOut() {
        mSignOutEvent.call()
    }
}