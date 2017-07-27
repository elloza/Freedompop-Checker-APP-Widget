package com.lozasolutions.fredompop.injection.component

import com.lozasolutions.fredompop.injection.PerFragment
import com.lozasolutions.fredompop.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent