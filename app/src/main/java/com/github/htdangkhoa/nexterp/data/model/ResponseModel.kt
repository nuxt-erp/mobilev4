package com.github.htdangkhoa.nexterp.data.model

import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
import com.google.gson.annotations.SerializedName

abstract class ResponseModel<T : Any?> {
    data class Error(
        @SerializedName("message")
        val message: String?
    )

    @SerializedName("code")
    val code: Int = 200

    @SerializedName("pagination")
    open val pagination: PaginationObject? = null

    abstract val data: T?

    @SerializedName("error")
    val error: Error? = null
}
