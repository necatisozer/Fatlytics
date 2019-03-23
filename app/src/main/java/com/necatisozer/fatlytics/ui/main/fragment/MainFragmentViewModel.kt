package com.necatisozer.fatlytics.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.necatisozer.fatlytics.domain.repository.SampleRepository
import com.necatisozer.fatlytics.extension.doInBackground
import com.necatisozer.fatlytics.helper.Logger
import com.necatisozer.fatlytics.helper.SingleLiveEvent
import com.necatisozer.fatlytics.ui.base.BaseViewModel
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