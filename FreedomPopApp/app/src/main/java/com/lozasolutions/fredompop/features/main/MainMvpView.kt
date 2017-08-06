package com.lozasolutions.fredompop.features.main

import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.features.base.MvpView

interface MainMvpView : MvpView {

    fun showUserUsage(userUsageResponse: UsageResponse)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}