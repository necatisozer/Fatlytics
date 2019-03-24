package com.fatlytics.app.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.domain.repository.SampleRepository
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val sampleRepository: SampleRepository,
    private val logger: Logger
) : BaseViewModel() {
    private val _liveData = MutableLiveData<Int>()
    val liveData: LiveData<Int>
        get() = _liveData

    private val _singleLiveEvent = SingleLiveEvent<Boolean>()
    val singleLiveEvent: LiveData<Boolean>
        get() = _singleLiveEvent

    init {
        addDisposable(
            sampleRepository.getSampleEntity().doInBackground().subscribeBy(
                onNext = { _liveData.value = it.id },
                onError = {
                    logger.e(it)
                    _singleLiveEvent.value = true
                }
            )
        )
    }
}