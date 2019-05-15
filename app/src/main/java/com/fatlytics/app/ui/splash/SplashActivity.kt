package com.fatlytics.app.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.fatlytics.app.R
import com.fatlytics.app.ui.base.BaseActivity
import com.fatlytics.app.ui.main.MainActivity
import com.fatlytics.app.ui.registration.RegistrationActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import splitties.activities.start
import splitties.toast.toast

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
            start<MainActivity>()
            finish()
        })

        viewModel.firebaseAuthEvent.observe(this, Observer {
            openAuthentication()
        })

        viewModel.registrationEvent.observe(this, Observer {
            start<RegistrationActivity>()
            finish()
        })

        viewModel.bannedEvent.observe(this, Observer {
            MaterialDialog(this).show {
                title(R.string.error)
                message(R.string.account_banned_error_message)
                positiveButton(android.R.string.ok) { dialog ->
                    AuthUI.getInstance()
                        .signOut(this@SplashActivity)
                        .addOnCompleteListener {
                            viewModel.checkUserAuth()
                        }
                }
                lifecycleOwner(this@SplashActivity)
            }
        })
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
                .setLogo(R.drawable.splash_logo)
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
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                response?.error?.localizedMessage?.let { toast(it) }
            }

            viewModel.checkUserAuth()
        }
    }
}