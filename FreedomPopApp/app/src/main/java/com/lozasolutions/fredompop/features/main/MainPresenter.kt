package com.lozasolutions.fredompop.features.main

import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.model.UsageResponse
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import com.lozasolutions.fredompop.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val fredompopAPI: FreedompopAPI, private val sessionManager: SessionManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun getPokemon(limit: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        fredompopAPI.getUserUsage()
                .compose(SchedulerUtils.ioToMain<UsageResponse>())
                .subscribe({ usage ->
                    mvpView?.showProgress(false)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun isTokenAvailable(): Boolean {
        return sessionManager.isTokenAvailable()
    }

}