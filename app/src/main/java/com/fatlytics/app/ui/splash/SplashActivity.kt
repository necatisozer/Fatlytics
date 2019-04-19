package com.fatlytics.app.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.fatlytics.app.R
import com.fatlytics.app.ui.base.BaseActivity
import com.fatlytics.app.ui.main.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import splitties.activities.start

class SplashActivity : BaseActivity<SplashViewModel>() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override val viewModelClass = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signedInEvent.observe(this, Observer {
            openApp()
        })

        viewModel.signInEvent.observe(this, Observer {
            openAuthentication()
        })
    }

    private fun openApp() {
        start<MainActivity>()
        finish()
    }

    private fun openAuthentication() {
        // Choose authentication providers
        val providers = arrayListOf(
            // AuthUI.IdpConfig.PhoneBuilder().build(),
            // AuthUI.IdpConfig.FacebookBuilder().build(),
            // AuthUI.IdpConfig.TwitterBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                // .setLogo(R.drawable.logo)
                .setTheme(R.style.Theme_Fatlytics_Light_DarkActionBar)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                openApp()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}