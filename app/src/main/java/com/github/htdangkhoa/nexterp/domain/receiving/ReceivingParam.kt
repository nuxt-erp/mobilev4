package com.github.htdangkhoa.nexterp.domain.receiving

import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam


class ReceivingParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_RECEIVING = 1
            const val GET_RECEIVING_DETAILS = 2
            const val UPDATE_RECEIVING = 3
        }
    }

    lateinit var receivingRequest: UpdateReceivingRequest
    var id: Int = 0

    constructor(type: Int, id: Int) : this(ReceivingParam.Type.GET_RECEIVING_DETAILS) {
        this.id = id
    }

    constructor(type: Int, id: Int, receivingRequest: UpdateReceivingRequest) : this(ReceivingParam.Type.UPDATE_RECEIVING) {
        this.receivingRequest = receivingRequest
        this.id = id
    }

}
