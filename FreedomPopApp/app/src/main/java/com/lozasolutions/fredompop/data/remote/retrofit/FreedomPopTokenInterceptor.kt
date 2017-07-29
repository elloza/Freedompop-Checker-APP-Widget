package com.lozasolutions.fredompop.data.remote.retrofit

/**
 * Created by Loza on 29/07/2017.
 */

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class FreedomPopTokenInterceptor(user: String, password: String) : Interceptor {

    private val credentials: String

    init {
        this.credentials = Credentials.basic(user, password)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }

}