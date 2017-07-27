package com.lozasolutions.fredompop.features.splash

import com.lozasolutions.fredompop.data.DataManager
import com.lozasolutions.fredompop.data.local.PreferencesHelper
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class SplashPresenter @Inject
constructor(private val mDataManager: DataManager, private val preferencesHelper: PreferencesHelper) : BasePresenter<SplashMvpView>() {

    override fun attachView(mvpView: SplashMvpView) {
        super.attachView(mvpView)
    }

    fun isTokenAvailable(): Boolean {
        return preferencesHelper.isTokenAvailable()
    }

}