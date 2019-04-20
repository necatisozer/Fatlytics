package com.fatlytics.app.domain.entity

import org.threeten.bp.LocalDate

data class PersonalInfo(
    val username: String,
    val firstName: String,
    val lastName: String,
    val birthday: LocalDate,
    val gender: Gender
)