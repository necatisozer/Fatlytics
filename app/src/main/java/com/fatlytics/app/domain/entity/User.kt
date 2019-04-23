package com.fatlytics.app.domain.entity

import org.threeten.bp.LocalDate

data class User(
    val banned: Boolean?,
    val birthday: LocalDate?,
    val dailyActiveness: DailyActiveness?,
    val diseases: List<Disease>?,
    val email: String?,
    val firstName: String?,
    val gender: Gender?,
    val height: String?,
    val lastName: String?,
    val token: String?,
    val uid: String?,
    val username: String?,
    val weight: String?
) : Entity

sealed class DailyActiveness {
    object NotActive : DailyActiveness()
    object Low : DailyActiveness()
    object Medium : DailyActiveness()
    object High : DailyActiveness()
}

sealed class Disease {
    object Diabetes : Disease()
    object Obesity : Disease()
    object Heart : Disease()
}

sealed class Gender {
    object Female : Gender()
    object Male : Gender()
}