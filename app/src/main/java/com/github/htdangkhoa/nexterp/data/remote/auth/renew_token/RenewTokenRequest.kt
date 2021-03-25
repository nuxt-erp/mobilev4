package com.github.htdangkhoa.nexterp.data.remote.auth.renew_token

import com.google.gson.annotations.SerializedName

data class RenewTokenRequest(
    @SerializedName("refresh_token")
    val refresh_token: String,
    val grant_type: String,
    val client_id: String,
    val client_secret: String
)
