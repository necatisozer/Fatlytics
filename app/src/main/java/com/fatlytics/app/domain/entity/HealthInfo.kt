package com.fatlytics.app.domain.entity

data class HealthInfo(
    val height: Int,
    val weight: Int,
    val dailyActiveness: DailyActiveness,
    val diseases: List<Disease>
)