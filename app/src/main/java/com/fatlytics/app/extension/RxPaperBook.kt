package com.fatlytics.app.extension

import com.fatlytics.app.helper.Resource
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Observable

fun <T> RxPaperBook.readResource(key: String, defaultValue: T? = null): Observable<Resource<T>> {
    val readValue = defaultValue?.let { read(key, it) } ?: read<T>(key)

    return readValue.toObservable()
        .map<Resource<T>> { Resource.Success(it) }
        .onErrorReturn { Resource.Error(Exception(it)) }
        .startWith(Resource.Loading())
}
