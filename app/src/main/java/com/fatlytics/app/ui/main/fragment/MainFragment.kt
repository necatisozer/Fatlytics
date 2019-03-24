package com.fatlytics.app.ui.main.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.fatlytics.app.R
import com.fatlytics.app.databinding.MainFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment
import splitties.alertdialog.appcompat.alert
import splitties.alertdialog.appcompat.cancelButton
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.okButton
import splitties.alertdialog.appcompat.onDismiss
import splitties.alertdialog.appcompat.onShow
import splitties.alertdialog.appcompat.positiveButton
import splitties.alertdialog.appcompat.title
import splitties.toast.toast
import splitties.views.textColorResource
import splitties.views.textResource

class MainFragment : BaseViewModelFragment<MainFragmentViewModel, MainFragmentBinding>() {
    override val layoutRes = R.layout.main_fragment
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        with(binding) {
            sampleTextView.textResource = R.string.app_name
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            liveData.observe(viewLifecycleOwner, Observer {
                toast("ID: $it")
            })

            singleLiveEvent.observe(viewLifecycleOwner, Observer {
                activity?.alert {
                    title = "error title"
                    message = "error message"
                    okButton { }
                    cancelButton()
                    onDismiss { }
                }?.onShow {
                    positiveButton.textColorResource = R.color.red_500
                }?.show()
            })
        }
    }
}
