package com.fatlytics.app.extension

import io.reactivex.Completable

fun Completable.asRemote() = toSingleDefault(Unit).asRemote()