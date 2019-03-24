package com.fatlytics.app.extension

// hashCode() for Nullable types
fun Any?.hashCode(): Int = this?.hashCode() ?: 0

fun Any.getClassTag(): String = this.javaClass.simpleName

fun Any.getMethodTag(): String =
    getClassTag() + object : Any() {}.javaClass.enclosingMethod?.name