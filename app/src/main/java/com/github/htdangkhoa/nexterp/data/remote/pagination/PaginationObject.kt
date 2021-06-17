package com.github.htdangkhoa.nexterp.data.remote.pagination

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PaginationObject(

    @SerializedName("current_page")
    var current_page: Int,

    @SerializedName("first_page_url")
    var first_page_url: String,

    @SerializedName("last_page")
    var last_page: Int,

    @SerializedName("last_page_url")
    var last_page_url: String,

    @SerializedName("per_page")
    var per_page: Int,

    @SerializedName("total")
    var total: Int

)