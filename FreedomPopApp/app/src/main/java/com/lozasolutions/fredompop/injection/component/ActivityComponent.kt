package com.lozasolutions.fredompop.injection.component

import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.login.LoginActivity
import com.lozasolutions.fredompop.features.main.MainActivity
import com.lozasolutions.fredompop.features.splash.SplashActivity
import com.lozasolutions.fredompop.injection.PerActivity
import com.lozasolutions.fredompop.injection.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(splashActivity: SplashActivity)

    fun inject(loginActivity: LoginActivity)

}
