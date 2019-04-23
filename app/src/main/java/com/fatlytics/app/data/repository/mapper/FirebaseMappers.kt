package com.fatlytics.app.data.repository.mapper

import com.fatlytics.app.data.source.firebase.entity.User
import com.fatlytics.app.domain.entity.DailyActiveness
import com.fatlytics.app.domain.entity.Disease
import com.fatlytics.app.domain.entity.Gender
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import com.fatlytics.app.domain.entity.User as UserEntity

private val dailyActivenessMap = mapOf(
    "daily_activeness/not_active" to DailyActiveness.NotActive,
    "daily_activeness/low" to DailyActiveness.Low,
    "daily_activeness/medium" to DailyActiveness.Medium,
    "daily_activeness/high" to DailyActiveness.High
)

private val diseaseMap = mapOf(
    "disease/diabetes" to Disease.Diabetes,
    "disease/heart" to Disease.Heart,
    "disease/obesity" to Disease.Obesity
)

private val genderMap = mapOf(
    "gender/female" to Gender.Female,
    "gender/male" to Gender.Male
)

fun User.mapToUserEntity() = UserEntity(
    banned = banned,
    birthday = LocalDate.parse(birthday, DateTimeFormatter.ISO_LOCAL_DATE),
    dailyActiveness = dailyActivenessMap[daily_activeness?.path],
    diseases = diseases?.flatMap {
        mutableListOf<Disease>().apply {
            diseases.forEach { diseaseMap[it.path]?.let { add(it) } }
        }
    },
    email = email,
    firstName = first_name,
    gender = genderMap[gender?.path],
    height = height,
    lastName = last_name,
    token = token,
    uid = uid,
    username = username,
    weight = weight
)