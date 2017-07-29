package com.lozasolutions.fredompop.features.login

import com.lozasolutions.fredompop.features.base.MvpView

interface LoginMvpView : MvpView {

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

    fun showMainScreen()

}