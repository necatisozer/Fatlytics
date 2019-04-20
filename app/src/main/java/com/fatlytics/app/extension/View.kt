package com.fatlytics.app.extension

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.visible() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.getInflater(): LayoutInflater = LayoutInflater.from(context)

fun View.onSingleClick(debounceTime: Long = 2000L, action: () -> Unit) {
    var lastClickTime: Long = 0
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime >= debounceTime) action()
        lastClickTime = SystemClock.elapsedRealtime()
    }
}

fun View.onSingleTouch(debounceTime: Long = 2000L, action: () -> Unit) {
    var lastClickTime: Long = 0
    setOnTouchListener { v, event ->
        if (SystemClock.elapsedRealtime() - lastClickTime >= debounceTime) action()
        lastClickTime = SystemClock.elapsedRealtime()
        true
    }
}
