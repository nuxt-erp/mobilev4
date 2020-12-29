package com.github.htdangkhoa.nexterp.data.remote.tag

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class TagResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Tag>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<TagResponse.Tag>>() {
    data class Tag(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("type")
        var type: String,

        @SerializedName("created_at")
        var created_at: Date,

        @SerializedName("updated_at")
        var updated_at: Date
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TagResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

