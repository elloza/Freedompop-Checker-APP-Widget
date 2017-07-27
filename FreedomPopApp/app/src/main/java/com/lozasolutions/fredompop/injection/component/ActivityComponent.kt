package com.lozasolutions.fredompop.injection.component

import com.lozasolutions.fredompop.injection.PerActivity
import com.lozasolutions.fredompop.injection.module.ActivityModule
import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.detail.DetailActivity
import com.lozasolutions.fredompop.features.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)
}
