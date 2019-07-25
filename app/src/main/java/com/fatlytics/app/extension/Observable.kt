package com.fatlytics.app.extension

import com.fatlytics.app.helper.Resource
import io.reactivex.Observable

fun <T, T2> Observable<Resource<T>>.mapResource(transform: (T) -> T2): Observable<Resource<T2>> {
    return map { it.map(transform) }
}

fun <T> Observable<T>.ignoreError(): Observable<T> = onErrorResumeNext(Observable.empty())
