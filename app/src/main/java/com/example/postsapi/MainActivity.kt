package com.example.postsapi

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postsapi.databinding.ActivityMainBinding
import com.example.postsapi.databinding.CreatePostDialogBinding
import com.example.postsapi.models.Posts
import com.example.postsapi.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), PostsAdapter.PostListener {

    private lateinit var  binding: ActivityMainBinding
    private lateinit var bindingDialog : CreatePostDialogBinding
    private lateinit var adapter: PostsAdapter
    private lateinit var vm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[HomeViewModel::class.java]
        initRecyclerView()
        vm.getAllPost()

        vm.postsModelListData?.observe(this, Observer {
            if (it != null){
                binding.rvPosts.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Posts>)
        }else{
            Toast.makeText(this, "Something went worng",Toast.LENGTH_SHORT).show()
            }
        })

        binding.fabAddPosts.setOnClickListener{
            showCreatePostsDialog()
        }
    }
    private fun initRecyclerView(){
        adapter = PostsAdapter(this)
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapter
    }

    override fun onItemDeleted(posts: Posts, position: Int) {
        TODO("Not yet implemented")
    }


    fun showCreatePostsDialog() {
        val dialog = Dialog(this)
        bindingDialog = CreatePostDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        var title = ""
        var body = ""
        bindingDialog.btnSavePost.setOnClickListener {
            title = bindingDialog.edtTitle.text.toString().trim()
            body = bindingDialog.edtPostBody.text.toString().trim()

            if(title.isNotEmpty() && body.isNotEmpty()){
                val posts = Posts()
                posts.userId = 1
                posts.title = title
                posts.body = body

                vm.createPost(posts)
                vm.createPostsLiveData?.observe(this, Observer {
                    if(it != null){
                        adapter.addData(posts)
                        binding.rvPosts.smoothScrollToPosition(0)
                    }else{
                        Toast.makeText(this, "Cannot create posts at the moment", Toast.LENGTH_SHORT).show()
                    }
                    dialog.cancel()
                })
            }else{
                Toast.makeText(this, "Please fill data carefully!", Toast.LENGTH_SHORT).show()
            }
        }
     dialog.show()
     val window = dialog.window
     window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}