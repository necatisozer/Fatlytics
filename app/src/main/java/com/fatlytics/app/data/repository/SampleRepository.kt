package com.fatlytics.app.data.repository

import com.fatlytics.app.data.repository.entity.SampleEntity
import com.fatlytics.app.data.source.api.Api
import com.fatlytics.app.data.source.api.toSampleEntity
import com.fatlytics.app.data.source.rxpaper.RxSampleBook
import com.fatlytics.app.extension.Device
import com.fatlytics.app.extension.asRemote
import com.fatlytics.app.extension.ignoreError
import com.fatlytics.app.extension.mapResource
import com.fatlytics.app.extension.readResource
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.Resource
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SampleRepository @Inject constructor(
    @RxSampleBook private val rxSampleBook: RxPaperBook,
    private val api: Api,
    private val logger: Logger
) {
    fun getSampleEntity(): Observable<Resource<SampleEntity>> {
        val localEntity = rxSampleBook.readResource<SampleEntity>("sample_key")
        val remoteEntity = api.getSample()
            .asRemote()
            .mapResource { it.toSampleEntity() }
            .doOnNext { rxSampleBook.write("sample_key", it) }

        val result =
            if (Device.hasInternetConnection) Observable.concat(
                localEntity.ignoreError(),
                remoteEntity
            )
            else localEntity

        return result.observeOn(AndroidSchedulers.mainThread())
    }
}