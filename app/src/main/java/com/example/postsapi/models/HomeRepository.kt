package com.example.postsapi.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.postsapi.network.APIService
import com.example.postsapi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeRepository {
    private var apiService: APIService? = null

    init {
        apiService = ApiClient.getApiClient().create(APIService::class.java)
    }

    fun fetchAllPosts(): LiveData<List<Posts>>{
        val data = MutableLiveData<List<Posts>>()

        apiService?.getPosts()?.enqueue(object : Callback<List<Posts>>{
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                val res = response.body()
                if (response.code() == 200 && res != null){
                    data.value = res
                }else{
                    data.value = null
                }
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
               data.value = null
            }

        })
        return data
    }

    fun createPosts(posts: Posts): LiveData<Posts>{
        val data = MutableLiveData<Posts>()
        apiService?.savePosts(posts)?.enqueue(object: Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                val res = response.body()
                if (response.code() == 201 && res != null) {
                    data.value = res
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }

    fun deletePosts(id: Int): LiveData<Boolean>{
        val data = MutableLiveData<Boolean>()
        apiService?.deletePosts(id)?.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.code() == 200
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = false
            }


        })
        return data
    }
}