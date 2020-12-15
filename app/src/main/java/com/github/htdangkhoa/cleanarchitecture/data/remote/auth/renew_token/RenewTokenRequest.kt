package com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token

import com.google.gson.annotations.SerializedName

data class RenewTokenRequest(
    @SerializedName("refresh_token")
    val refresh_token: String,
    val grant_type: String,
    val client_id: Int,
    val client_secret: String
)
