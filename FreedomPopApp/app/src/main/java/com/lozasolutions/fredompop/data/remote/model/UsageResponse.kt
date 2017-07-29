package com.lozasolutions.fredompop.data.model

data class UsageResponse(val baseBandwidth: Long, val viralBoost: Long, val percentUsed: Float, val overageUsed: Long,
                         val planLimitUsed: Long, val balanceRemaining: Long, val upgradable: Boolean,val offerBonusEarned: Long,
                         val startTime: Long, val endTime: Long)
