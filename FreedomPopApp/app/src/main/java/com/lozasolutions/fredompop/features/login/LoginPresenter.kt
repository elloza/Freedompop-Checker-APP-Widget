package com.lozasolutions.fredompop.features.login

import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.model.LoginResponse
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import com.lozasolutions.fredompop.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class LoginPresenter @Inject
constructor(private val freedompopAPI: FreedompopAPI, private val sessionManager: SessionManager) : BasePresenter<LoginMvpView>() {

    override fun attachView(mvpView: LoginMvpView) {
        super.attachView(mvpView)
    }

    fun login(username: String, password: String) {

        checkViewAttached()
        mvpView?.showProgress(true)
        freedompopAPI.login(username = username, password = password)
                .compose(SchedulerUtils.ioToMain<LoginResponse>())
                .subscribe({ loginResponse ->
                    sessionManager.saveTokenInfo(loginResponse.access_token,loginResponse.refresh_token)
                    mvpView?.showProgress(false)
                    mvpView?.showMainScreen()
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}