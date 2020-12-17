package com.github.htdangkhoa.nexterp.extension

import android.content.Context
import com.github.htdangkhoa.nexterp.R

val Context.appName: String
    get() = this.resources.getString(R.string.app_name)
