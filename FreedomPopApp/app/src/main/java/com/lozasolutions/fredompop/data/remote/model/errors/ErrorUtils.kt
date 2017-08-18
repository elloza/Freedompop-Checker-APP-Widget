package com.lozasolutions.fredompop.data.remote.model.errors

import com.google.gson.GsonBuilder
import com.lozasolutions.fredompop.data.remote.model.ErrorResponse
import retrofit2.Response



/**
 * Created by Loza on 18/08/2017.
 */
object ErrorUtils {

    fun parseError(response: Response<*>): ErrorResponse {

        val gson = GsonBuilder().create()
        val errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse::class.java)
        return errorResponse
    }

    fun parseErrorFromString(response:String): ErrorResponse {

        val gson = GsonBuilder().create()
        val errorResponse = gson.fromJson(response, ErrorResponse::class.java)
        return errorResponse
    }

}