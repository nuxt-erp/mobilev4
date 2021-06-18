package com.github.htdangkhoa.nexterp.domain.tag


class TagParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_TAG = 1
        }

    }
    var list: Int = 0
    var per_page: Int? = 20

    constructor(type: Int, list : Int, per_page : Int?) : this(TagParam.Type.GET_TAG) {
        this.list = list
        this.per_page = per_page
    }
}
