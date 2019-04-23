package com.fatlytics.app.data.repository.mapper

import com.fatlytics.app.data.source.firebase.entity.HealthInfo
import com.fatlytics.app.data.source.firebase.entity.PersonalInfo
import com.fatlytics.app.data.source.firebase.entity.User
import com.fatlytics.app.domain.entity.DailyActiveness
import com.fatlytics.app.domain.entity.Disease
import com.fatlytics.app.domain.entity.Gender
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import com.fatlytics.app.domain.entity.HealthInfo as HealthInfoEntity
import com.fatlytics.app.domain.entity.PersonalInfo as PersonalInfoEntity
import com.fatlytics.app.domain.entity.User as UserEntity

fun User.toUserEntity() = UserEntity(
    banned = banned,
    email = email,
    healthInfo = healthInfo?.toHealthInfoEntity(),
    personalInfo = personalInfo?.toPersonalInfoEntity(),
    token = token,
    uid = uid
)

fun HealthInfo.toHealthInfoEntity() = HealthInfoEntity(
    height = height?.toInt(),
    weight = weight?.toInt(),
    dailyActiveness = dailyActivenessMap[dailyActiveness],
    diseases = diseases?.mapNotNull { diseaseMap[it] }
)

fun PersonalInfo.toPersonalInfoEntity() = PersonalInfoEntity(
    username = username,
    firstName = firstName,
    lastName = lastName,
    birthday = birthday?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) },
    gender = genderMap[gender]
)

private val dailyActivenessMap = mapOf(
    "not-active" to DailyActiveness.NOT_ACTIVE,
    "low" to DailyActiveness.LOW,
    "medium" to DailyActiveness.MEDIUM,
    "high" to DailyActiveness.HIGH
)

private val diseaseMap = mapOf(
    "diabetes" to Disease.DIABETES,
    "obesity" to Disease.OBESITY,
    "heart" to Disease.HEART
)

private val genderMap = mapOf(
    "male" to Gender.MALE,
    "female" to Gender.FEMALE
)