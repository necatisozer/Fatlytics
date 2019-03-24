package com.fatlytics.app.extension

import android.os.Build
import com.fatlytics.app.BuildConfig

inline fun debug(body: () -> Unit) {
    if (BuildConfig.DEBUG) body()
}

inline fun targetApi(version: Int, body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= version) body.invoke()
}





