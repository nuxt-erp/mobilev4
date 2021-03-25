package com.github.htdangkhoa.nexterp.domain.config

class ConfigParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_CONFIG = 1
        }
    }
}
