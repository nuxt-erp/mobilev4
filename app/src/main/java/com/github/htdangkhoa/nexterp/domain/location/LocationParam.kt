package com.github.htdangkhoa.nexterp.domain.location


class LocationParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_LOCATIONS = 1
        }
    }
}
