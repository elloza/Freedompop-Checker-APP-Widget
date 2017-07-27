package com.lozasolutions.fredompop.data.local

import android.content.Context
import android.content.SharedPreferences
import com.lozasolutions.fredompop.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesHelper
@Inject
constructor(@ApplicationContext context: Context) : SessionManager{

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
    }

}
