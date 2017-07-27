package com.lozasolutions.fredompop.injection.component

import android.app.Application
import android.content.Context
import com.lozasolutions.fredompop.data.DataManager
import com.lozasolutions.fredompop.data.local.PreferencesHelper
import com.lozasolutions.fredompop.data.remote.MvpStarterService
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

    fun dataManager(): DataManager

    fun mvpBoilerplateService(): MvpStarterService

    fun preferenceHelper():PreferencesHelper
}
