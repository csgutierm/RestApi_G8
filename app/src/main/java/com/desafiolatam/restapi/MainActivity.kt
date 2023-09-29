package com.desafiolatam.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.model.Post
import com.desafiolatam.restapi.model.PostAdapter
import com.desafiolatam.restapi.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var postsList: ArrayList<Post> = arrayListOf()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewAdapter = PostAdapter(postsList)
        val postsRecyclerView = findViewById<RecyclerView>(R.id.postsRecyclerView)
        postsRecyclerView.adapter = viewAdapter

        loadApiData()
    }

    private fun loadApiData() {
        val service = RetrofitClient.retrofitInstance()
        val call = service.getAllPosts()
        call.enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                response.body()?.map {
                    Log.d("MAIN", "${it.id} - ${it.title}")
                    postsList.add(it)
                }
                viewAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<ArrayList<Post>>, t:
            Throwable) {
                Log.d("MAIN", "Error: " + t)
                Toast.makeText(applicationContext,
                    "Error: no pudimos recuperar los posts desde el api",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

