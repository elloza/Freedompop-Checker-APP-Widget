package com.lozasolutions.fredompop.data.remote.jobs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.lozasolutions.fredompop.FreedompopChecker
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.InfoManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.model.errors.ErrorUtils
import com.lozasolutions.fredompop.features.splash.SplashActivity
import retrofit2.HttpException
import timber.log.Timber


/**
 * Created by Loza on 06/08/2017.
 */
class AlertJob(val api: FreedompopAPI, val sessionManager: SessionManager, val alertManager: AlertManager, val infoManager: InfoManager) : Job() {

    private val NOTIFICATION_ID = 42


    fun doWork(): Job.Result {

        //DO WORK
        try {

            if (!alertManager.isAlertActive()) {

                if (sessionManager.isTokenAvailable()) {

                    val usageResponse = api.getUserUsage().blockingGet()
                    //Save info
                    infoManager.setLastUsageObtained(usageResponse)

                    //Send info to widget
                    // TODO

                    val inMB = 1024 * 1024
                    val usedInMB = usageResponse.planLimitUsed / inMB
                    val planLimitedUsedInMB = usageResponse.totalLimit /inMB

                    val left = planLimitedUsedInMB - usedInMB
                    //val alertAmount = alertManager.getAmountAlertInMB()
                    val alertAmount = 100

                    if (left <= alertAmount) {

                        val content = java.lang.String.format(context.getString(R.string.notification_content),
                                usedInMB, planLimitedUsedInMB)


                        sendNotification(context.getString(R.string.notification_title), content)
                    }

                    //Update current data in shared preferences
                    //val interval = alertManager.getAlertIntervalInMillisecond()
                    val interval = 10000L

                    val mJobManager = FreedompopChecker.get(context).component.jobManager()
                    mJobManager.schedule(buildJobRequest(interval))
                    Timber.e("NEXT JOB SCHEDULED")
                }
            }

        } catch (e: HttpException) {

            val errorResponse = ErrorUtils.parseError(e.response())

            if (errorResponse.error == "invalid_grant")
                return Job.Result.SUCCESS

            Timber.e("RESCHEDULE")
            e.printStackTrace()
            return Job.Result.RESCHEDULE
        }

        Timber.e("SUCCESS")
        return Job.Result.SUCCESS


    }


    override fun onRunJob(params: Job.Params): Job.Result {

        Timber.e("Executing Job")
        return doWork()
    }

    fun sendNotification(title: String, content: String) {

        Timber.e("Sending Notification")

        //Present a notification to the user
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        //Create action intent
        val action = Intent(context, SplashActivity::class.java)

        //CLEAR STACK ACTIVITIES
        action.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val operation = PendingIntent.getActivity(context, 0, action, PendingIntent.FLAG_CANCEL_CURRENT)

        // The id of the channel.
        val CHANNEL_ID = "my_channel_01"
        // Create a notification and set the notification channel.

        var notification: Notification? = null

        val notificationLargeIconBitmap = BitmapFactory.decodeResource(
                context.getApplicationContext().getResources(),
                R.mipmap.ic_launcher)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            val channel = NotificationChannel(CHANNEL, title, NotificationManager.IMPORTANCE_DEFAULT)
            manager?.createNotificationChannel(channel)
            notification = Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(notificationLargeIconBitmap)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(operation)
                    .setChannelId(CHANNEL)
                    .build()

        } else {
            notification = NotificationCompat.Builder(context)
                    .setLargeIcon(notificationLargeIconBitmap)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(operation)
                    .build()
        }


        manager?.notify(NOTIFICATION_ID, notification)

    }


    override fun onReschedule(newJobId: Int) {
        // the rescheduled job has a new ID
        Timber.e("onReschedule")
    }

    companion object {

        const val TAG = "alert_job_tag"
        const val CHANNEL = "FREEDOMPOP_CHANNEL"
        fun buildJobRequest(interval: Long): JobRequest {

            Timber.e("buildJobRequest")

            return JobRequest.Builder(AlertJob.TAG)
                    .setUpdateCurrent(false)
                    .setExact(interval)
                    .setBackoffCriteria(30, JobRequest.BackoffPolicy.LINEAR)
                    .setPersisted(true)
                    .build()
        }

    }
}