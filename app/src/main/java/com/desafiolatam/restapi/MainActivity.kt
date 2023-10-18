package com.desafiolatam.restapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.consumo_API.pojo.Photo
import com.desafiolatam.restapi.consumo_API.clientes.RetrofitClient
import com.desafiolatam.restapi.consumo_API.pojo.Categoria
import com.desafiolatam.restapi.consumo_API.pojo.Producto
import com.desafiolatam.restapi.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var usersList: ArrayList<User> = arrayListOf()
    private var photosList: ArrayList<Photo> = arrayListOf()
    private var categoriasList: ArrayList<Categoria> = arrayListOf()
    private var productosList: ArrayList<Producto> = arrayListOf()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val service = RetrofitClient.retrofitInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*        this.viewAdapter = UserAdapter(usersList)
                val usersRecyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
                usersRecyclerView.adapter = viewAdapter*/
        /**
         *          this.viewAdapter = PhotoAdapter(photosList)
         *          val photosRecyclerView = findViewById<RecyclerView>(R.id.photosRecyclerView)
         *          photosRecyclerView.adapter = viewAdapter
         *
         */
        this.viewAdapter = CategoriaAdapter(categoriasList)
        val categoriasRecyclerView = findViewById<RecyclerView>(R.id.categoriasRecyclerView)
        categoriasRecyclerView.adapter = viewAdapter

        categoriasRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this, categoriasRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        val categoriaSeleccionada = categoriasList[position]

                        val intent = Intent(this@MainActivity, CategoriaActivity::class.java)

                        // datos pasados al nuevo Activity si es necesario
                        intent.putExtra("categoriaId", categoriaSeleccionada.id)
                        intent.putExtra("categoriaNombre", categoriaSeleccionada.name)
                        intent.putExtra("categoriaCreation", categoriaSeleccionada.creationAt)
                        intent.putExtra("categoriaImage", categoriaSeleccionada.image)
                        intent.putExtra("categoriaUpdate", categoriaSeleccionada.updatedAt)

                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        // TODO
                    }
                })
        )

        loadApiData()

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            showCreateUserDialog()
        }
    }

    private fun loadApiData() {

        val callCategorias = service.getAllCategorias()
        val callProductos = service.getAllProductos()

        callCategorias.enqueue(object : Callback<ArrayList<Categoria>> {
            override fun onResponse(
                call: Call<ArrayList<Categoria>>,
                response: Response<ArrayList<Categoria>>
            ) {
                if (response.isSuccessful) {
                    Log.i("Exito Categoria",response.toString())

                    Log.i("Exito Categoria",response.body().toString())
                } else {
                    Log.i("Error Categoria",response.toString())
                }

                response.body()?.let { newCategorias ->
                    val startPosition = categoriasList.size
                    categoriasList.addAll(newCategorias)
                    viewAdapter.notifyItemRangeInserted(startPosition, newCategorias.size)
                }
            }

            override fun onFailure(call: Call<ArrayList<Categoria>>, t: Throwable) {
                Log.d("MAIN", "Error: $t")
                Toast.makeText(applicationContext,
                    "Error: no pudimos recuperar las categorias desde la api",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        /*callProductos.enqueue(object : Callback<ArrayList<Producto>> {
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
        })*/
    }


    private fun showCreateUserDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_user, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Crear Usuario")

        val alertDialog = builder.create()
        alertDialog.show()

        val buttonEnviar = dialogView.findViewById<Button>(R.id.buttonEnviar)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)

        buttonEnviar.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()

            val service = RetrofitClient.retrofitInstance()
            /*            val call = service.createUser(User(1,name,"test", email, phone))

                        call.enqueue(object : Callback<User> {
                            override fun onResponse(
                                call: Call<User>,
                                response: Response<User>
                            ) {
                                if (response.isSuccessful) {
                                    Log.i("Exito",response.toString())
                                } else {
                                    Log.i("Error",response.toString())
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.d("MAIN", "Error: $t")
                                Toast.makeText(
                                    applicationContext,
                                    "Error: no pudimos crear el usuario",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })*/

            /*            lifecycleScope.launch {
                            createUserAndHandleResponse(name, email, phone)
                            alertDialog.dismiss()
                        }*/
        }
    }

    /*private suspend fun createUserAndHandleResponse(
        name: String,
        email: String,
        phone: String
    ) {

        try {
            val response = withContext(Dispatchers.IO) {
                service.createUser(User(1, name, "test", email, phone)).execute()
            }

            if (response.isSuccessful) {
                Log.i("Exito", response.toString())
            } else {
                Log.i("Error", response.toString())
            }
        } catch (e: Exception) {
            Log.d("MAIN", "Error: $e")
            Toast.makeText(
                applicationContext,
                "Error: no pudimos crear el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }*/
}

