package com.necatisozer.fatlytics.domain.repository

import com.necatisozer.fatlytics.domain.entity.SampleEntity
import io.reactivex.Observable

interface SampleRepository : Repository {
    fun getSampleEntity(): Observable<SampleEntity>
}