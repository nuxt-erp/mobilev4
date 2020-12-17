package com.github.htdangkhoa.nexterp.domain.user

class UserParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_ME = 1

            const val GET_USERS = 2
        }
    }
}
