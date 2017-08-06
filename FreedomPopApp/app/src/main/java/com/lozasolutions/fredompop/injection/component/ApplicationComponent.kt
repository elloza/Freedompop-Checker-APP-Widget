package com.lozasolutions.fredompop.injection.component

import android.app.Application
import android.content.Context
import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.lozasolutions.fredompop.data.local.AlertManager
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.jobs.AlertJobCreator
import com.lozasolutions.fredompop.injection.ApplicationContext
import com.lozasolutions.fredompop.injection.module.ApplicationModule
import com.lozasolutions.fredompop.injection.module.JobsModule
import dagger.Component
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class,JobsModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun freedompopAPI(): FreedompopAPI

    fun sessionManager():SessionManager

    fun jobManager():JobManager

    fun alertManager(): AlertManager

    fun jobCreator(): AlertJobCreator

    fun provideMapProvidersJobs(): Map<String, Provider<Job>>
}
