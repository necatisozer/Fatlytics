package com.fatlytics.app.ui.splash

import android.os.Bundle
import com.fatlytics.app.ui.base.BaseActivity
import com.fatlytics.app.ui.main.MainActivity
import splitties.activities.start

class SplashActivity : BaseActivity<SplashViewModel>() {
    override val viewModelClass = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start<MainActivity>()
    }
}