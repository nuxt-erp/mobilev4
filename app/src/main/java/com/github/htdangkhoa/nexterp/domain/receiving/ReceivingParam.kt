package com.github.htdangkhoa.nexterp.domain.receiving

import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam


class ReceivingParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_RECEIVING = 1
            const val GET_RECEIVING_DETAILS = 2
        }
    }

    var id: Int = 0
    constructor(type: Int, id: Int) : this(ReceivingParam.Type.GET_RECEIVING_DETAILS) {
        this.id = id
    }

}
