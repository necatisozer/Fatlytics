package com.fatlytics.app.domain.repository

import com.fatlytics.app.domain.entity.SampleEntity
import io.reactivex.Observable

interface SampleRepository : Repository {
    fun getSampleEntity(): Observable<SampleEntity>
}