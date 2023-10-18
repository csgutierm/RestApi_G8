package com.desafiolatam.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.consumo_API.clientes.RetrofitClient
import com.desafiolatam.restapi.consumo_API.pojo.Categoria
import com.desafiolatam.restapi.consumo_API.pojo.Producto
import com.desafiolatam.restapi.model.CategoriaAdapter
import com.desafiolatam.restapi.model.ProductoAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaActivity : AppCompatActivity() {

    private var productosList: ArrayList<Producto> = arrayListOf()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val service = RetrofitClient.retrofitInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        val categoriaImage = intent.getStringExtra("categoriaImage")
        val imageView = findViewById<ImageView>(R.id.imageView)

        Log.i("IMAGEN", categoriaImage.toString())
        if (categoriaImage != null) {
            Picasso.get().load(categoriaImage).error(R.drawable.capture3).into(imageView)
        } else Picasso.get().load(R.drawable.capture3).into(imageView)

        this.viewAdapter = ProductoAdapter(productosList)
        val productosRecyclerView = findViewById<RecyclerView>(R.id.productosRecyclerView)
        productosRecyclerView.adapter = viewAdapter

        loadApiData()
    }

    private fun loadApiData() {

        val callProductos = service.getAllProductos()

        callProductos.enqueue(object : Callback<ArrayList<Producto>> {
            override fun onResponse(
                call: Call<ArrayList<Producto>>,
                response: Response<ArrayList<Producto>>
            ) {
                if (response.isSuccessful) {
                    Log.i("Exito Producto",response.toString())

                    Log.i("Exito Producto",response.body().toString())
                } else {
                    Log.i("Error Producto",response.toString())
                }

                response.body()?.let { newProductos ->
                    val startPosition = productosList.size
                    productosList.addAll(newProductos)
                    viewAdapter.notifyItemRangeInserted(startPosition, newProductos.size)
                }
            }

            override fun onFailure(call: Call<ArrayList<Producto>>, t: Throwable) {
                Log.d("MAIN", "Error: $t")
                Toast.makeText(applicationContext,
                    "Error: no pudimos recuperar las productos desde la api",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}