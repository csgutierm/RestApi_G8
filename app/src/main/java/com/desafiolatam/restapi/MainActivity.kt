package com.desafiolatam.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val service = RetrofitClient.retrofitInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        this.viewAdapter = UserAdapter(usersList)
        val usersRecyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        usersRecyclerView.adapter = viewAdapter*/
        this.viewAdapter = PhotoAdapter(photosList)
        val photosRecyclerView = findViewById<RecyclerView>(R.id.photosRecyclerView)
        photosRecyclerView.adapter = viewAdapter

        loadApiData()

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            showCreateUserDialog()
        }
    }

    private fun loadApiData() {

       /* val call = service.getAllUsers()
        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                response.body()?.let { newUsers ->
                    val startPosition = usersList.size
                    usersList.addAll(newUsers)
                    viewAdapter.notifyItemRangeInserted(startPosition, newUsers.size)
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t:
            Throwable) {
                Log.d("MAIN", "Error: $t")
                Toast.makeText(applicationContext,
                    "Error: no pudimos recuperar los posts desde la api",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })*/
        val call = service.getAllPhotos()
        call.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(
                call: Call<ArrayList<Photo>>,
                response: Response<ArrayList<Photo>>
            ) {
                    if (response.isSuccessful) {
                        Log.i("Exito",response.toString())

                        Log.i("Exito",response.body().toString())
                    } else {
                        Log.i("Error",response.toString())
                    }

                response.body()?.let { newPhotos ->
                    val startPosition = photosList.size
                    photosList.addAll(newPhotos)
                    viewAdapter.notifyItemRangeInserted(startPosition, newPhotos.size)
                }
            }
            override fun onFailure(call: Call<ArrayList<Photo>>, t:
            Throwable) {
                Log.d("MAIN", "Error: $t")
                Toast.makeText(applicationContext,
                    "Error: no pudimos recuperar las fotos desde la api",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
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
            val call = service.createUser(User(1,name,"test", email, phone))

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
            })

            lifecycleScope.launch {
                createUserAndHandleResponse(name, email, phone)
                alertDialog.dismiss()
            }
        }
    }

    private suspend fun createUserAndHandleResponse(
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
    }
}

