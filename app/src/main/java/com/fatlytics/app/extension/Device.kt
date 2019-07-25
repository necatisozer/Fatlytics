package com.fatlytics.app.extension

import android.os.Build
import splitties.systemservices.connectivityManager

object Device {
    val hasInternetConnection get() = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true

    val emulator
        get() = Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || Build.PRODUCT == "google_sdk"
}