package com.github.htdangkhoa.nexterp.data.remote.auth

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    override val data: Token?
) : ResponseModel<AuthResponse.Token?>() {
    data class Token(
        @SerializedName("access_token")
        val access_token: String,

        @SerializedName("refresh_token")
        val refresh_token: String
    )
}
