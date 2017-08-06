package com.lozasolutions.fredompop.data.remote.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import java.lang.Exception


/**
 * Created by Loza on 06/08/2017.
 */
class AlertJob(val api: FreedompopAPI, val sessionManager: SessionManager, val alertManager: AlertManager ) : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {

        if(alertManager.isAlertActive()){

            if(sessionManager.isTokenAvailable()){

                //DO WORK
                try {
                    val usageResponse = api.getUserUsage().toFuture().get();
                    val  inMB = 1024*1024
                    val usedInMB = usageResponse.planLimitUsed / inMB
                    val planLimitedUsedInMB = usageResponse.totalLimit /inMB

                    if((planLimitedUsedInMB - usedInMB) < alertManager.getAmountAlertInMB() ){
                        //TODO show alert only if showed today
                        showAlertNotification()
                    }

                    //Update current data in shared preferences

                    buildJobRequest(alertManager.getAlertIntervalInMillisecond()).schedule()

                    return Job.Result.SUCCESS


                }catch (e:Exception){
                    return Job.Result.RESCHEDULE
                }

            }else{
                Job.Result.SUCCESS
            }

        }
        return Job.Result.SUCCESS
    }

    override fun onReschedule(newJobId: Int) {
        // the rescheduled job has a new ID

    }


    fun showAlertNotification(){
        //TODO show notification with the current available data / alerted data with intent to the splash screen

    }

    companion object {

        const val TAG = "alert_job_tag"

        fun buildJobRequest(interval:Long): JobRequest {
            return JobRequest.Builder(AlertJob.TAG)
                    .setExact(interval)
                    .setBackoffCriteria(30,JobRequest.BackoffPolicy.LINEAR)
                    .setUpdateCurrent(true)
                    .setPersisted(true)
                    .build()
        }

    }
}