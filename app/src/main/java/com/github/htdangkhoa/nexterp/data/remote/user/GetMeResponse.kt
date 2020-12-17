package com.github.htdangkhoa.nexterp.data.remote.user

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class GetMeResponse(
    @SerializedName("data")
    override val data: User?
) : ResponseModel<GetMeResponse.User?>() {
    data class User(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("roles")
        var roles: Set<String>

    )
}
