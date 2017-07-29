package com.lozasolutions.fredompop.data.remote

import com.lozasolutions.fredompop.data.model.*
import io.reactivex.Single

/**
 * Created by Loza on 29/07/2017.
 */
interface FreedompopAPI {

    fun login(username: String, password: String) : Single<LoginResponse>

    fun getUserUsage(): Single<UsageResponse>

    fun getUserInfo(): Single<InfoResponse>

    fun getPlans(): Single<PlansResponse>

    fun getPlan(): Single<PlanResponse>

    fun getPlan(idPlan : String): Single<PlanResponse>

    fun getServices(): Single<ServicesResponse>

    fun getService(): Single<ServiceResponse>

    fun getService(idService: String): Single<ServiceResponse>

    fun getContacts(): Single<ContactsResponse>

    fun getFriends(): Single<FriendsResponse>

}