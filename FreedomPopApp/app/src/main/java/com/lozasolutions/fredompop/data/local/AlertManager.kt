package com.lozasolutions.fredompop.data.local

/**
 * Created by Loza on 27/07/2017.
 */
interface AlertManager {

    fun saveAlert( alertIntervalInMilliseconds:Long, amountAlertInMB:Int)

    fun getAlertIntervalInMillisecond():Long

    fun getAmountAlertInMB():Int

    fun setActiveAlert(state:Boolean)

    fun isAlertActive(): Boolean

}