package com.lozasolutions.fredompop.injection.module

import android.app.Application
import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.jobs.AlertJob
import com.lozasolutions.fredompop.data.remote.jobs.AlertJobCreator
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
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
        return JobManager.instance()
    }

    @Provides
    @Singleton
    internal fun provideJobCreator(): AlertJobCreator {
        return AlertJobCreator()
    }

    @Provides
    @IntoMap
    @Singleton
    @StringKey( AlertJob.TAG )
    internal fun provideUpdateAndCheckInfoJob(api: FreedompopAPI, sessionManager: SessionManager, alertManager: AlertManager): Job {
        return AlertJob(api, sessionManager,alertManager)
    }


}