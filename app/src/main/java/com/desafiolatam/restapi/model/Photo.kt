package com.desafiolatam.restapi.model

import com.google.gson.annotations.SerializedName


data class Photo(
    @SerializedName("id")
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)