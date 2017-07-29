package com.lozasolutions.fredompop.data.remote.retrofit


import com.lozasolutions.fredompop.data.model.LoginResponse
import com.lozasolutions.fredompop.data.model.UsageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FreedompopAPIService {

    @POST("auth/token")
    fun login(@Query("username") username: String,@Query("password") password: String,@Query("grant_type") grantType: String = "password"): Single<LoginResponse>

    @POST("auth/token")
    fun refreshToken(@Query("refresh_token") refreshToken: String,@Query("grant_type") grantType: String = "refresh_token"): Single<LoginResponse>

    @GET("user/usage")
    fun getUserUsage(@Query("accessToken") accessToke: String): Single<UsageResponse>


}
