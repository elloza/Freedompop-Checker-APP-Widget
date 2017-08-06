package com.lozasolutions.fredompop.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("email")val email: String, @SerializedName("access_token") val access_token: String,
                         @SerializedName("token_type") val token_type: String, @SerializedName("expires_in") val expires_in: Int,
                         @SerializedName("refresh_token") val refresh_token: String)