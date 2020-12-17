package com.github.htdangkhoa.nexterp.domain.receiving


class ReceivingParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_RECEIVING = 1
        }
    }
}
