package com.github.htdangkhoa.nexterp.data.remote.category

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class CategoryResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Category>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<CategoryResponse.Category>>() {
    data class Category(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("dear_id")
        var dear_id: Int,

        @SerializedName("is_enabled")
        var is_enabled: Int,

        @SerializedName("disabled_at")
        var disabled_at: Date,

        @SerializedName("created_at")
        var created_at: Date,

        @SerializedName("updated_at")
        var updated_at: Date
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
