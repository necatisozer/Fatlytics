package com.fatlytics.app.data.source.api

import com.fatlytics.app.data.repository.entity.SampleEntity
import com.fatlytics.app.data.source.api.entity.SampleApiEntity

fun SampleApiEntity.toSampleEntity() = SampleEntity(id = id)