package com.github.htdangkhoa.nexterp.extension

import com.github.htdangkhoa.nexterp.data.model.ResponseExceptionModel
import com.github.htdangkhoa.nexterp.data.model.ResponseModel

inline fun <reified T> Result.Companion.map(responseModel: ResponseModel<T>): Result<T> {
    return if (responseModel.code != 200 && responseModel.data == null) {
        failure(ResponseExceptionModel(responseModel))
    } else {
        success(responseModel.data!!)
    }
}
