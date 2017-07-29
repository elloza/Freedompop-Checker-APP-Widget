package com.lozasolutions.fredompop.data.remote.retrofit

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lozasolutions.fredompop.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Provide "make" methods to create instances of [MvpStarterService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object FreedompopServiceFactory {

    fun makeFreedompopService(): FreedompopAPIService {
        return makeFreedompopService(makeGson())
    }

    private fun makeFreedompopService(gson: Gson): FreedompopAPIService {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.FREEDOMPOP_API_URL)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(FreedompopAPIService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()

        //TODO Investigate this! I dont know but this api only works with this authentication...
        // Sadly, this API effect will not last long :(

        val username = "3726328870"
        val password = "pNp6TIgVm4viVadoyoUdxbsrfmiBwudN"

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
            httpClientBuilder.addInterceptor(FreedomPopTokenInterceptor(username, password))
            httpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return httpClientBuilder.build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create()
    }
}
