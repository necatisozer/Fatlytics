package com.fatlytics.app.ui.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fatlytics.app.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import splitties.toast.toast
import javax.inject.Inject

abstract class BaseActivity<M : BaseViewModel> :
    DaggerAppCompatActivity() {

    protected abstract val viewModelClass: Class<M>

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: M

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass).also {
            it.unexpectedErrorEvent.observe(
                this,
                Observer { toast(R.string.unexpected_error_message) })
        }
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}