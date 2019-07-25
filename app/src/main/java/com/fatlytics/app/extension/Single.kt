package com.fatlytics.app.extension

import com.fatlytics.app.helper.Resource
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Single<T>.asRemote(): Observable<Resource<T>> {
    return toObservable()
        .map<Resource<T>> { Resource.Success(it) }
        .onErrorReturn { Resource.Error(Exception(it)) }
        .startWith(Resource.Loading())
}