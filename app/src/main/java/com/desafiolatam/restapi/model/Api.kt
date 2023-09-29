package com.desafiolatam.restapi.model

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.ArrayList

interface Api {

    @GET("/posts")
    fun getAllPosts(): Call<ArrayList<Post>>

    @DELETE("/posts/{postId}")
    fun deletePost(@Path("postId") postId: Int?): Call<Void>

}