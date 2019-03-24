package com.fatlytics.app.ui.main.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.fatlytics.app.R
import com.fatlytics.app.databinding.MainFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment
import com.fatlytics.app.ui.splash.SplashActivity
import com.firebase.ui.auth.AuthUI
import splitties.fragments.start
import splitties.views.onClick

class MainFragment : BaseViewModelFragment<MainFragmentViewModel, MainFragmentBinding>() {
    override val layoutRes = R.layout.main_fragment
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.logoutButton.onClick { viewModel.onSignOut() }
    }

    private fun initViewModel() {
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            binding.infoTextView.text = it
        })

        viewModel.signOutEvent.observe(viewLifecycleOwner, Observer {
            context?.let {
                AuthUI.getInstance()
                    .signOut(it)
                    .addOnCompleteListener {
                        start<SplashActivity>()
                        activity?.finish()
                    }
            }
        })
    }
}
