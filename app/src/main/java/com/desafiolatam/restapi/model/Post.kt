package com.desafiolatam.restapi.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("user_id")
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)