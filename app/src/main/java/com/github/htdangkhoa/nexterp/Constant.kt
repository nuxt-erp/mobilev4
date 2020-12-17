package com.github.htdangkhoa.nexterp

import com.chibatching.kotpref.KotprefModel

object Constant : KotprefModel() {
    var BASE_URL: String by stringPref(BuildConfig.BASE_URL)
    var CLIENT_SECRET: String by stringPref(BuildConfig.CLIENT_SECRET)
    var CLIENT_ID: Int by intPref(BuildConfig.CLIENT_ID)
    var GRANT_TYPE: String by stringPref(BuildConfig.GRANT_TYPE)

}
