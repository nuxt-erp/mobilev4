package com.github.htdangkhoa.nexterp.domain.category


class CategoryParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_CATEGORIES = 1
        }

    }
    var list: Int = 0
    var is_enabled: Int = 0

    constructor(type: Int, list : Int, is_enabled: Int) : this(CategoryParam.Type.GET_CATEGORIES) {
        this.list = list
        this.is_enabled = is_enabled
    }
}
