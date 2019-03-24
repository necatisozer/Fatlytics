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
    private val _userInfo = MutableLiveData<String>()
    val userInfo: LiveData<String>
        get() = _userInfo

    private val _signOutEvent = SingleLiveEvent<Void>()
    val signOutEvent: LiveData<Void>
        get() = _signOutEvent

    init {
        val user = FirebaseAuth.getInstance().currentUser

        val info = """
            |Display name: ${user?.displayName}
            |Email: ${user?.email}
            |Phone number: ${user?.phoneNumber}
            |UID: ${user?.uid}
            """.trimMargin()

        _userInfo.value = info
    }

    fun onSignOut() {
        _signOutEvent.call()
    }
}