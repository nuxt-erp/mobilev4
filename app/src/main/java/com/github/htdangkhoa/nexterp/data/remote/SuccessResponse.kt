package com.github.htdangkhoa.nexterp.data.remote

import com.github.htdangkhoa.nexterp.data.model.ResponseModel

data class SuccessResponse(
    override val data: Array<String?>
) : ResponseModel<Array<String?>>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SuccessResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
