package com.github.htdangkhoa.nexterp.data.remote

import com.github.htdangkhoa.nexterp.data.model.ResponseModel

data class SuccessResponse(
    override val data: String?
) : ResponseModel<String>()
