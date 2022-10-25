package com.example.postsapi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.postsapi.models.HomeRepository
import com.example.postsapi.models.Posts

class HomeViewModel(application: Application): AndroidViewModel(application){

    private var homeRepository: HomeRepository? = null
    var postsModelListData: LiveData<List<Posts>> ? = null
    var createPostsLiveData: LiveData<Posts>? = null
    var deletePostsLiveData: LiveData<Boolean>? = null


    init {
        homeRepository = HomeRepository()
        postsModelListData = MutableLiveData()
        createPostsLiveData = MutableLiveData()
        deletePostsLiveData = MutableLiveData()
    }

    fun getAllPost(){
        postsModelListData = homeRepository?.fetchAllPosts()
    }

    fun createPost(posts: Posts){
        createPostsLiveData = homeRepository?.createPosts(posts)
    }

    fun deletePost(id: Int){
        deletePostsLiveData = homeRepository?.deletePosts(id)
    }
}