package com.github.htdangkhoa.nexterp.data.remote.auth.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("client_secret")
    var clientSecret: String,
    @SerializedName("grant_type")
    var grantType: String
)
