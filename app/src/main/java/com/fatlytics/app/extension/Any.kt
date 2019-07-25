package com.fatlytics.app.extension

fun Any?.isNull() = this == null
val Any.classTag get() = this.javaClass.simpleName
val Any.methodTag get() = classTag + object : Any() {}.javaClass.enclosingMethod?.name
