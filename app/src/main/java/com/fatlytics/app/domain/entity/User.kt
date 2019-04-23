package com.fatlytics.app.domain.entity

import org.threeten.bp.LocalDate

data class User(
    val banned: Boolean?,
    val email: String?,
    val healthInfo: HealthInfo?,
    val personalInfo: PersonalInfo?,
    val token: String?,
    val uid: String?
) : Entity

data class HealthInfo(
    val height: Int?,
    val weight: Int?,
    val dailyActiveness: DailyActiveness?,
    val diseases: List<Disease>?
) : Entity

data class PersonalInfo(
    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val birthday: LocalDate?,
    val gender: Gender?
) : Entity

enum class DailyActiveness { NOT_ACTIVE, LOW, MEDIUM, HIGH }
enum class Disease { DIABETES, OBESITY, HEART }
enum class Gender { MALE, FEMALE }