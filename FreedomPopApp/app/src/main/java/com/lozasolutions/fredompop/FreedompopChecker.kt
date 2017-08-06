package com.lozasolutions.fredompop

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.evernote.android.job.JobManager
import com.facebook.stetho.Stetho
import com.lozasolutions.fredompop.data.remote.jobs.AlertJobCreator
import com.lozasolutions.fredompop.injection.component.ApplicationComponent
import com.lozasolutions.fredompop.injection.component.DaggerApplicationComponent
import com.lozasolutions.fredompop.injection.module.ApplicationModule
import com.squareup.leakcanary.LeakCanary
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig



class FreedompopChecker : MultiDexApplication() {

    internal var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        }

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("Exo2-Bold.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            mApplicationComponent = applicationComponent
        }

    companion object {

        operator fun get(context: Context): FreedompopChecker {
            return context.applicationContext as FreedompopChecker
        }
    }
}
