package com.lozasolutions.fredompop.data.remote.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


/**
 * Created by Loza on 06/08/2017.
 */
@Singleton
class AlertJobCreator : JobCreator {

    @Inject lateinit var jobs: Map<String, Provider<Job>>

    @Inject
    fun AlertJobCreator(){}

    override fun create(tag: String): Job? {
        val jobProvider = jobs.get(tag)
        Timber.d("jobProvider != null: %b", jobProvider != null)
        val job = jobProvider?.get()
        return job
    }
}