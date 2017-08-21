package com.lozasolutions.fredompop.data.remote.retrofit

/**
 * Created by Loza on 29/07/2017.
 */

import com.lozasolutions.fredompop.data.local.InfoManager
import com.lozasolutions.fredompop.data.remote.model.errors.ErrorUtils
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset


class FreedomPopTokenInterceptor(user: String, password: String, val infoManager: InfoManager) : Interceptor {

    private val credentials: String

    init {
        this.credentials = Credentials.basic(user, password)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
        val response = chain.proceed(authenticatedRequest)


        if (response.code() in 400..500) {

            val responseBody = response.body()
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"))

            try {
                val error = ErrorUtils.parseErrorFromString(responseBodyString)
                //TODO If invalid authentication send event that must be received in every activity
                if (error.error == "invalid_grant") {
                    //infoManager.clearAllUserInfo()

                }

            } catch (e: Exception) {
                e.printStackTrace()
                return response
            }

        }
        return response

    }

}