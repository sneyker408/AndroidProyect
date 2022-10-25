package com.example.postsapi.models

import com.google.gson.annotations.SerializedName

data class Posts(

    var userId: Int? = 0,

    var id: Int? = 0,

    var title: String? = "",

    var body: String? = "",
)
