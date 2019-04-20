package com.fatlytics.app.extension

import org.threeten.bp.LocalDate
import java.util.Calendar

fun Calendar.toLocalDate(): LocalDate = LocalDate.of(
    get(Calendar.YEAR),
    get(Calendar.MONTH),
    get(Calendar.DAY_OF_MONTH)
)