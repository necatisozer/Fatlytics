package com.fatlytics.app.data.repository.mapper

import com.fatlytics.app.data.source.api.entity.SampleApiEntity
import com.fatlytics.app.domain.entity.SampleEntity

fun SampleApiEntity.toSampleEntity() = SampleEntity(id = id)