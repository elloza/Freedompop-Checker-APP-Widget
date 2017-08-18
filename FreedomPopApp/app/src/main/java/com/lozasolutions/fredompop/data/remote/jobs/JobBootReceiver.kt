package com.lozasolutions.fredompop.data.remote.jobs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.evernote.android.job.JobManagerCreateException
import com.evernote.android.job.util.JobApi
import com.lozasolutions.fredompop.FreedompopChecker
import timber.log.Timber


/**
 * Created by Loza on 18/08/2017.
 */
/**
 * A `BroadcastReceiver` rescheduling jobs after a reboot, if the underlying [JobApi] can't
 * handle it.

 * @author rwondratschek
 */
class JobBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /*
         * Create the job manager. We may need to reschedule jobs and some applications aren't initializing the
         * manager in Application.onCreate(). It may happen that some jobs can't be created if the JobCreator
         * wasn't registered, yet. Apps / Libraries need to figure out how to solve this themselves.
         */
        try {
            Timber.e("JOB BOOT RECEIVER")
            FreedompopChecker.get(context).component.jobManager()
        } catch (ignored: JobManagerCreateException) {
        }

    }
}