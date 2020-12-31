package com.github.htdangkhoa.nexterp.domain.receiving

import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam


class ReceivingParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_RECEIVING = 1
            const val GET_RECEIVING_DETAILS = 2
            const val UPDATE_RECEIVING = 3
            const val VOID_RECEIVING = 4
            const val FINISH_RECEIVING = 5
            const val NEW_RECEIVING = 6

        }
    }

    lateinit var receivingRequest: UpdateReceivingRequest
    lateinit var newReceivingRequest: NewReceivingRequest

    var id: Int = 0

    constructor(type: Int, id: Int) : this(type) {
        this.id = id
    }

    constructor(type: Int, newReceivingRequest: NewReceivingRequest) : this(ReceivingParam.Type.NEW_RECEIVING) {
        this.newReceivingRequest = newReceivingRequest
    }

    constructor(type: Int, id: Int, receivingRequest: UpdateReceivingRequest) : this(ReceivingParam.Type.UPDATE_RECEIVING) {
        this.receivingRequest = receivingRequest
        this.id = id
    }

}
