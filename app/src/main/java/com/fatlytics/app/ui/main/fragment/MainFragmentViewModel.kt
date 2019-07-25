package com.fatlytics.app.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.repository.SampleRepository
import com.fatlytics.app.data.repository.entity.SampleEntity
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.Resource
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val sampleRepository: SampleRepository,
    private val logger: Logger
) : BaseViewModel() {
    private val viewState = MutableLiveData<MainFragmentViewState>()
    fun getViewState(): LiveData<MainFragmentViewState> = viewState

    init {
        sampleRepository.getSampleEntity()
            .subscribe(::onMoviesResultReady)
            .also { disposables += it }
    }

    private fun onMoviesResultReady(sampleEntityResource: Resource<SampleEntity>) {
        viewState.value = MainFragmentViewState(sampleEntityResource)
    }
}