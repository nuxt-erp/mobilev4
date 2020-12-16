package com.github.htdangkhoa.cleanarchitecture.data.remote.location

import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Location>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<LocationResponse.Location>>() {
    data class Location(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

