package com.necatisozer.fatlytics.ui.splash

import android.os.Bundle
import com.necatisozer.fatlytics.ui.base.BaseActivity
import com.necatisozer.fatlytics.ui.main.MainActivity
import splitties.activities.start

class SplashActivity : BaseActivity<SplashViewModel>() {
    override val viewModelClass = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start<MainActivity>()
    }
}