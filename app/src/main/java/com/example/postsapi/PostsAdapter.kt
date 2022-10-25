package com.example.postsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapi.databinding.ItemPostsBinding
import com.example.postsapi.models.Posts

class PostsAdapter(var lister: PostListener):
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var postList: ArrayList<Posts>? = null

    interface PostListener{
        fun onItemDeleted(posts: Posts, position: Int)
    }

    fun setData(list: ArrayList<Posts>){
        postList = list
        notifyDataSetChanged()
    }

    fun addData(posts: Posts){
        postList?.add(0,posts)
        notifyItemChanged(0)
    }

    fun  removeData(position: Int){
        postList?.removeAt(position)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PostsViewHolder(layoutInflater.inflate(R.layout.item_posts,parent,false))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = postList?.get(position)
        holder.bind(item!!)

        holder.itemView.setOnClickListener{
            item?.let { it1->
                lister.onItemDeleted(it1,position)
            }
        }
    }

    override fun getItemCount(): Int = postList?.size ?:0

    inner class PostsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemPostsBinding.bind(view)

        fun bind(posts: Posts){
            binding.postTitle.text = posts.title
            binding.postBody.text = posts.body

            //Listener para el botón eliminar
            binding.btnDeletePost.setOnClickListener{lister.onItemDeleted(posts,adapterPosition)}
        }
    }
}