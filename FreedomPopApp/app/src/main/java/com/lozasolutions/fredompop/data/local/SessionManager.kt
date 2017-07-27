package com.lozasolutions.fredompop.data.local

/**
 * Created by Loza on 27/07/2017.
 */
interface SessionManager {

    fun saveTokenInfo( token:String, refreshToken:String)

    fun isTokenAvailable(): Boolean

    fun getToken(): String

    fun getRefreshToken(): String

}