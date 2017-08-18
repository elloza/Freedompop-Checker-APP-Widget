package com.lozasolutions.fredompop.data.local

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.gson.Gson
import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.features.widget.UsageWidgetProvider
import com.lozasolutions.fredompop.injection.ApplicationContext
import javax.inject.Inject




class PreferencesHelper
@Inject
constructor(@ApplicationContext val context: Context) : SessionManager, AlertManager,InfoManager{

    override fun getLastUsageAvailable(): UsageResponse {
        val gson = Gson()
        val json = mPref.getString(LAST_INFO, "")
        val obj = gson.fromJson<UsageResponse>(json, UsageResponse::class.java)
        return obj
    }

    override fun setLastUsageObtained(usageResponse: UsageResponse) {

        val gson = Gson()
        val json = gson.toJson(usageResponse)
        mPref.edit().putString(LAST_INFO, json).apply()

        updateActivityWidget(usageResponse)
    }

    fun updateActivityWidget(usageResponse: UsageResponse) {

        val intent = Intent(context, UsageWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val extras = Bundle()
        extras.putParcelable(UsageWidgetProvider.LAST_USAGE,usageResponse)

        // get widgets ids for this provider
        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, UsageWidgetProvider::class.java!!))
        if (ids != null && ids.isNotEmpty()) {
            extras.putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            intent.putExtras(extras)
            context.sendBroadcast(intent)
        }
    }

    override fun clearAllUserInfo() {
        mPref.edit().clear().apply()
    }

    override fun saveAlert(alertIntervalInMilliseconds: Long, amountAlertInMB: Int) {
        mPref.edit().putLong(ALERT_INTERVAL, alertIntervalInMilliseconds).apply()
        mPref.edit().putInt(ALERT_MB,amountAlertInMB).apply()
    }

    override fun getAlertIntervalInMillisecond(): Long {
        return mPref.getLong(ALERT_INTERVAL,0)
    }

    override fun getAmountAlertInMB(): Int {
        return mPref.getInt(ALERT_MB,0)
    }

    override fun setActiveAlert(state: Boolean) {
        return mPref.edit().putBoolean(ALERT_ACTIVE,state).apply()
    }

    override fun isAlertActive(): Boolean {
        return mPref.getBoolean(ALERT_ACTIVE,false)
    }

    override fun saveTokenInfo(token: String, refreshToken: String) {
        mPref.edit().putString(TOKEN,token).apply()
        mPref.edit().putString(REFRESH_TOKEN,refreshToken).apply()
    }

    override fun isTokenAvailable(): Boolean {
        return !mPref.getString(TOKEN,"").isBlank()
    }

    override fun getToken(): String {
        return mPref.getString(TOKEN,"")
    }

    override fun getRefreshToken(): String {
        return mPref.getString(REFRESH_TOKEN,"")
    }


    private val mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref.edit().clear().apply()
    }

    companion object {
        val PREF_FILE_NAME = "mvpstarter_pref_file"
        val TOKEN = "token"
        val REFRESH_TOKEN = "refresh_token"
        val ALERT_INTERVAL = "alert_interval"
        val ALERT_ACTIVE = "alert_active"
        val ALERT_MB = "alert_mb"
        val LAST_INFO = "last_info"
    }

}
