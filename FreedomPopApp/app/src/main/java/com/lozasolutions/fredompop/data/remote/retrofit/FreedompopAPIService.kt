package com.lozasolutions.fredompop.data.remote.retrofit


import com.lozasolutions.fredompop.data.remote.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FreedompopAPIService {

    @POST("auth/token")
    fun login(@Query("username") username: String,@Query("password") password: String,@Query("grant_type") grantType: String = "password"): Single<LoginResponse>

    @POST("auth/token")
    fun refreshToken(@Query("refresh_token") refreshToken: String,@Query("grant_type") grantType: String = "refresh_token"): Single<LoginResponse>

    @GET("user/usage")
    fun getUserUsage(@Query("accessToken") accessToke: String): Single<UsageResponse>

    @GET("user/info")
    fun getUserInfo(@Query("accessToken") accessToke: String): Single<InfoResponse>

    @GET("plans")
    fun getPlans(@Query("accessToken") accessToke: String): Single<PlansResponse>

    @GET("plan")
    fun getUserPlan(@Query("accessToken") accessToke: String): Single<PlanResponse>

    @GET("plan/{id}")
    fun getUserPlanById(@Query("accessToken") accessToke: String,@Path("id") id:String): Single<PlanResponse>

    @GET("services")
    fun getServices(@Query("accessToken") accessToke: String): Single<ServicesResponse>

    @GET("service")
    fun getService(@Query("accessToken") accessToke: String): Single<ServiceResponse>

    @GET("service/{id}")
    fun getUserServiceById(@Query("accessToken") accessToke: String,@Path("id") id:String): Single<ServiceResponse>

    @GET("friends")
    fun getFriends(@Query("accessToken") accessToke: String): Single<FriendsResponse>

    @GET("contacts")
    fun getContacts(@Query("accessToken") accessToke: String): Single<ContactsResponse>

}
