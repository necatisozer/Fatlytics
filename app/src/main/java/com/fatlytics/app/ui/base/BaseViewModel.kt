package com.fatlytics.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatlytics.app.helper.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val mUnexpectedErrorEvent = SingleLiveEvent<Void>()
    val unexpectedErrorEvent: LiveData<Void> get() = mUnexpectedErrorEvent

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}