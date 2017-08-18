package com.lozasolutions.fredompop.injection.module

import android.app.Application
import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.InfoManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.jobs.AlertJobCreator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
/**
 * Created by Loza on 06/08/2017.
 */
@Module
class JobsModule {
    @Provides
    @Singleton
    internal fun provideJobManager(application: Application, jobCreator: AlertJobCreator): JobManager {
        JobManager.create(application).addJobCreator(jobCreator)
        val mJobManager = JobManager.instance()
        mJobManager.config.isGcmApiEnabled = true
        mJobManager.config.isVerbose = true
        return mJobManager
    }

    @Provides
    @Singleton
    internal fun provideJobCreator(api: FreedompopAPI, sessionManager: SessionManager, alertManager: AlertManager, infoManager: InfoManager): AlertJobCreator {
        return AlertJobCreator(sessionManager,api, alertManager, infoManager)
    }

}