package com.lozasolutions.fredompop.features.splash

import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class SplashPresenter @Inject
constructor(private val sessionManager: SessionManager) : BasePresenter<SplashMvpView>() {

    override fun attachView(mvpView: SplashMvpView) {
        super.attachView(mvpView)
    }

    fun isTokenAvailable(): Boolean {
        return sessionManager.isTokenAvailable()
    }

}