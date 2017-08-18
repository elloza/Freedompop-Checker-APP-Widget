package com.lozasolutions.fredompop.data.local

import com.lozasolutions.fredompop.data.remote.model.UsageResponse

/**
 * Created by Loza on 27/07/2017.
 */
interface InfoManager {

    fun getLastUsageAvailable():UsageResponse

    fun setLastUsageObtained(usageResponse: UsageResponse)

    fun clearAllUserInfo()

}