package com.github.htdangkhoa.cleanarchitecture.domain.user

import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest

class UserParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_ME = 1

            const val GET_USERS = 2
        }
    }
}
