package com.lozasolutions.fredompop.features.main

import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.jobs.AlertJob
import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import com.lozasolutions.fredompop.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val fredompopAPI: FreedompopAPI, private val sessionManager: SessionManager,
            private  val jobManager: JobManager,private val alertManager: AlertManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun getUserUsage() {
        checkViewAttached()
        mvpView?.showProgress(true)


        fredompopAPI.getUserUsage()
                .compose(SchedulerUtils.ioToMain<UsageResponse>())
                .subscribe({ usage ->
                    mvpView?.showUserUsage(usage)
                    mvpView?.showProgress(false)
                    rescheduleAlert(true)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }

    }

    fun rescheduleAlert(active:Boolean){

        if(active){
            jobManager.cancelAllForTag(AlertJob.TAG)
            jobManager.schedule(AlertJob.buildJobRequest(2000))

        }else{
            jobManager.cancelAllForTag(AlertJob.TAG)
        }

    }

    fun isTokenAvailable(): Boolean {
        return sessionManager.isTokenAvailable()
    }

}