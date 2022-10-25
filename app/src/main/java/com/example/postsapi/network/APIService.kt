package com.example.postsapi.network

import com.example.postsapi.models.Posts
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("posts")
    fun getPosts(): Call<List<Posts>>

    @POST("posts")
    fun savePosts(@Body posts: Posts): Call<Posts>

    @DELETE("posts/{id}")
    fun deletePosts(@Path("id")id :Int): Call<String>
}