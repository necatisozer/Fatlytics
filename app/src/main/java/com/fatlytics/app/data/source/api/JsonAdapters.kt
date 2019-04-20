package com.fatlytics.app.data.source.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class DateAdapter {
    @ToJson
    fun toJson(date: LocalDate): String = DateTimeFormatter.ISO_LOCAL_DATE.format(date)

    @FromJson
    fun fromJson(date: String): LocalDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
}