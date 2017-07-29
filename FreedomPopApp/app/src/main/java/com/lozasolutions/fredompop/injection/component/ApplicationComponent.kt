package com.lozasolutions.fredompop.injection.component

import android.app.Application
import android.content.Context
import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.injection.ApplicationContext
import com.lozasolutions.fredompop.injection.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun freedompopAPI(): FreedompopAPI

    fun sessionManager():SessionManager
}
