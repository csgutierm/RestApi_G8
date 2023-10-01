package com.desafiolatam.restapi.model

import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface Api {

    @GET("/posts")
    fun getAllPosts(): Call<ArrayList<Post>>

    @DELETE("/posts/{postId}")
    fun deletePost(@Path("postId") postId: Int?): Call<Void>

    @GET("/users")
    fun getAllUsers(): Call<ArrayList<User>>

    @POST("/users")
    fun createUser(@Body user: User): Call<User>

    @GET("/photos")
    fun getAllPhotos(): Call<ArrayList<Photo>>

    @POST("/photos")
    fun createPhoto(@Body photo: Photo): Call<Photo>



}