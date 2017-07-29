package com.lozasolutions.fredompop.data.remote.retrofit

import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.model.*
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Loza on 29/07/2017.
 */
@Singleton
class FreedompopAPIRetrofit  @Inject
constructor(private val freedompopAPIService: FreedompopAPIService, private val sesionManager: SessionManager) : FreedompopAPI {


    override fun login(username: String, password: String): Single<LoginResponse> {
        return freedompopAPIService.login(username,password)
    }

    override fun getUserUsage(): Single<UsageResponse> {
        return freedompopAPIService.getUserUsage(sesionManager.getToken())
    }

    override fun getUserInfo(): Single<InfoResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlans(): Single<PlansResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlan(): Single<PlanResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlan(idPlan: String): Single<PlanResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServices(): Single<ServicesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getService(): Single<ServiceResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getService(idService: String): Single<ServiceResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContacts(): Single<ContactsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFriends(): Single<FriendsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}