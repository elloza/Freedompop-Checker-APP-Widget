package com.lozasolutions.fredompop.data.remote.model

import android.os.Parcel
import android.os.Parcelable

data class UsageResponse(val baseBandwidth: Long, val viralBoost: Long, val percentUsed: Float, val overageUsed: Long,
                         val planLimitUsed: Long, val balanceRemaining: Long, val upgradable: Boolean, val offerBonusEarned: Long,
                         val totalLimit: Long, val startTime: Long, val endTime: Long) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readLong(),
            source.readFloat(),
            source.readLong(),
            source.readLong(),
            source.readLong(),
            1 == source.readInt(),
            source.readLong(),
            source.readLong(),
            source.readLong(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(baseBandwidth)
        writeLong(viralBoost)
        writeFloat(percentUsed)
        writeLong(overageUsed)
        writeLong(planLimitUsed)
        writeLong(balanceRemaining)
        writeInt((if (upgradable) 1 else 0))
        writeLong(offerBonusEarned)
        writeLong(totalLimit)
        writeLong(startTime)
        writeLong(endTime)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UsageResponse> = object : Parcelable.Creator<UsageResponse> {
            override fun createFromParcel(source: Parcel): UsageResponse = UsageResponse(source)
            override fun newArray(size: Int): Array<UsageResponse?> = arrayOfNulls(size)
        }
    }
}