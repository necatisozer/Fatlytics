package com.fatlytics.app.ui.main.home

import androidx.lifecycle.LiveData
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val logger: Logger
) : BaseViewModel() {
    private val mSingleLiveEvent = SingleLiveEvent<Void>()
    val singleLiveEvent: LiveData<Void> get() = mSingleLiveEvent

    init {

    }
}