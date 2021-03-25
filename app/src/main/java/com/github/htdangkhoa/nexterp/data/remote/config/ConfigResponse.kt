package com.github.htdangkhoa.nexterp.data.remote.config

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class ConfigResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Config>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<ConfigResponse.Config>>() {
    data class Config(
        @SerializedName("id")
        var id: Int,

        @SerializedName("bins_mandatory")
        var bins_mandatory: Boolean,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfigResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

