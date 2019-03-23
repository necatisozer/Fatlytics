package com.necatisozer.fatlytics.data.repository

import com.necatisozer.fatlytics.data.repository.mapper.toSampleEntity
import com.necatisozer.fatlytics.data.source.api.Api
import com.necatisozer.fatlytics.data.source.rxpaper.RxSampleBook
import com.necatisozer.fatlytics.domain.entity.SampleEntity
import com.necatisozer.fatlytics.domain.repository.SampleRepository
import com.necatisozer.fatlytics.helper.Logger
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import splitties.systemservices.connectivityManager
import javax.inject.Inject

class ProdSampleRepository @Inject constructor(
    @RxSampleBook private val rxSampleBook: RxPaperBook,
    private val api: Api,
    private val logger: Logger
) : SampleRepository {
    override fun getSampleEntity(): Observable<SampleEntity> {
        val storedEntity = rxSampleBook.read<SampleEntity>("sample_key", SampleEntity())
        val updatedEntity = api.getSample().map { it.toSampleEntity() }.doOnSuccess {
            rxSampleBook.write("sample_key", it)
                .subscribeBy(
                    onComplete = { logger.i("Sample entity was stored") },
                    onError = { logger.w(it, "Sample entity couldn't be stored") }
                )
        }

        return if (connectivityManager.activeNetworkInfo?.isConnected == true)
            Single.concat(storedEntity, updatedEntity).toObservable()
        else storedEntity.toObservable()
    }
}