package com.lozasolutions.fredompop.data.remote.jobs

import android.content.Context
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.FreedompopChecker
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.InfoManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import java.lang.RuntimeException
import javax.inject.Singleton


/**
 * Created by Loza on 06/08/2017.
 */
@Singleton
class AlertJobCreator(val sessionManager: SessionManager, val api: FreedompopAPI, val alertManager: AlertManager, val infoManager: InfoManager) : JobCreator {

    override fun create(tag: String): Job? {
        when (tag) {
            AlertJob.TAG -> return AlertJob(api,sessionManager,alertManager,infoManager)
            else -> { // Note the block
                throw RuntimeException()
            }
        }
    }

    class AddReceiver : JobCreator.AddJobCreatorReceiver() {
        protected override fun addJobCreator(context: Context, manager: JobManager) {
            manager.addJobCreator(FreedompopChecker.get(context).component.jobCreator());
        }
    }
}