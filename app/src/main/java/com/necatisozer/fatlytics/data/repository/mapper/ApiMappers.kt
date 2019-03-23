package com.necatisozer.fatlytics.data.repository.mapper

import com.necatisozer.fatlytics.data.source.api.entity.SampleApiEntity
import com.necatisozer.fatlytics.domain.entity.SampleEntity

fun SampleApiEntity.toSampleEntity() = SampleEntity(id = id)