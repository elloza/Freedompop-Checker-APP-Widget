package com.lozasolutions.fredompop.data.remote.retrofit

import com.lozasolutions.fredompop.data.local.SessionManager
import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.data.remote.model.*
import com.lozasolutions.fredompop.data.remote.model.errors.ErrorUtils
import io.reactivex.Single
import io.reactivex.SingleSource
import retrofit2.HttpException
import java.lang.Exception
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

    fun checkTokenAndRefresh():Single<Boolean>{

        if(sesionManager.isTokenAvailable()){
            return freedompopAPIService.refreshToken(sesionManager.getRefreshToken()).flatMap { t: LoginResponse -> checkUserLoggedAndSaveData(t) }
        }else{
            return Single.error(InvalidAuthException("Invalid Auth", Throwable()))
        }
    }

    fun checkError(throwable: Throwable):SingleSource<LoginResponse>{

        if(throwable is HttpException){
            val error = ErrorUtils.parseError((throwable as HttpException).response())
            if(error.error.equals("invalid_grant"))
                return Single.error(InvalidAuthException("Invalid Auth",throwable))
        }

        return Single.error(UnknowErrorException("Unknown Error",throwable))

    }

    fun checkUserLoggedAndSaveData(loginResponse: LoginResponse):Single<Boolean>{
        if(!loginResponse.access_token.isNullOrBlank()) {
            sesionManager.saveTokenInfo(loginResponse.access_token, loginResponse.refresh_token)
            return Single.just(true)
        }else{
            return Single.error(Exception("Unable to get access token"))
        }
    }

    override fun getUserUsage(): Single<UsageResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getUserUsage(sesionManager.getToken())}
    }

    override fun getUserInfo(): Single<InfoResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getUserInfo(sesionManager.getToken())}
    }

    override fun getPlans(): Single<PlansResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getPlans(sesionManager.getToken())}
    }

    override fun getPlan(): Single<PlanResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getUserPlan(sesionManager.getToken())}
    }

    override fun getPlan(idPlan: String): Single<PlanResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getUserPlanById(sesionManager.getToken(),idPlan)}
    }

    override fun getServices(): Single<ServicesResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getServices(sesionManager.getToken())}
    }

    override fun getService(): Single<ServiceResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getService(sesionManager.getToken())}
    }

    override fun getService(idService: String): Single<ServiceResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getUserServiceById(sesionManager.getToken(),idService)}
    }

    override fun getContacts(): Single<ContactsResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getContacts(sesionManager.getToken())}
    }

    override fun getFriends(): Single<FriendsResponse> {
        return checkTokenAndRefresh().flatMap { t -> freedompopAPIService.getFriends(sesionManager.getToken())}
    }


}



