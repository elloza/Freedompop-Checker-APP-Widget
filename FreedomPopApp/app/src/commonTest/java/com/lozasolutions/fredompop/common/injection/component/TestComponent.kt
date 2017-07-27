package com.lozasolutions.fredompop.common.injection.component

import com.lozasolutions.fredompop.common.injection.module.ApplicationTestModule
import com.lozasolutions.fredompop.injection.component.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : ApplicationComponent