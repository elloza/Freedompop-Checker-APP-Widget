package com.lozasolutions.fredompop.features.main

import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.InfoManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.jobs.AlertJob
import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.data.remote.model.errors.ErrorUtils
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import com.lozasolutions.fredompop.util.rx.scheduler.SchedulerUtils
import retrofit2.HttpException
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val fredompopAPI: FreedompopAPI, private val sessionManager: SessionManager,
            private  val jobManager: JobManager,private val alertManager: AlertManager, private  val infoManager: InfoManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun clearUserInfo(){
        infoManager.clearAllUserInfo()
        infoManager.clearInfoWidget()
        jobManager.cancelAllForTag(AlertJob.TAG)
    }



    fun getUserUsage() {
        checkViewAttached()
        mvpView?.showProgress(true)


        fredompopAPI.getUserUsage()
                .compose(SchedulerUtils.ioToMain<UsageResponse>())
                .subscribe({ usage ->
                    infoManager.setLastUsageObtained(usage)
                    mvpView?.showUserUsage(usage)
                    mvpView?.showProgress(false)
                    rescheduleAlert(true)
                }) { throwable ->
                    if(throwable is HttpException) {
                        val error = ErrorUtils.parseError((throwable as HttpException).response())
                        //TODO send unauthenticated event notification
                    }else{

                    }
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }

    }

    fun rescheduleAlert(active:Boolean){

        if(active){
            jobManager.cancelAllForTag(AlertJob.TAG)
            jobManager.schedule(AlertJob.buildJobRequest(10000))

        }else{
            jobManager.cancelAllForTag(AlertJob.TAG)
        }

    }

    fun isTokenAvailable(): Boolean {
        return sessionManager.isTokenAvailable()
    }

}