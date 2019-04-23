package com.fatlytics.app.data.source.firebase.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val banned: Boolean? = null,
    val createdAt: Long? = null,
    val email: String? = null,
    val healthInfo: HealthInfo? = null,
    val personalInfo: PersonalInfo? = null,
    val token: String? = null,
    val uid: String? = null,
    val updatedAt: Long? = null
) : FirebaseEntity

@IgnoreExtraProperties
data class HealthInfo(
    val dailyActiveness: String? = null,
    val diseases: List<String>? = null,
    val height: Long? = null,
    val weight: Long? = null
) : FirebaseEntity

@IgnoreExtraProperties
data class PersonalInfo(
    val birthday: String? = null,
    val firstName: String? = null,
    val gender: String? = null,
    val lastName: String? = null,
    val username: String? = null
) : FirebaseEntity