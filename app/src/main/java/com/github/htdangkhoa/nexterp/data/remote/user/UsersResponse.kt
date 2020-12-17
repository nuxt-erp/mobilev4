package com.github.htdangkhoa.nexterp.data.remote.user

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<User>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<UsersResponse.User>>() {
    data class User(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UsersResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

