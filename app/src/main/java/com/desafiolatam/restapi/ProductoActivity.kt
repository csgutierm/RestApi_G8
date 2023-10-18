package com.desafiolatam.restapi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductoActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)


        // Obtener los valores de los Intent
        val productoId = intent.getIntExtra("productoId", 0) // El segundo argumento es el valor por defecto si no se encuentra el extra
        val productoNombre = intent.getStringExtra("productoNombre")
        val productoCreation = intent.getStringExtra("productoCreation")
        val productoPrecio = intent.getStringExtra("productoPrecio")
        val productoDescripcion = intent.getStringExtra("productoDescripcion")

        val ratingBar = findViewById<RatingBar>(R.id.producto_rating)
        val commentEditText = findViewById<EditText>(R.id.producto_comment)

        // Asignar los valores almacenados en las preferencias compartidas
        val productoRatingKey = "productoRating_$productoId"
        val productoCommentKey = "productoComment_$productoId"

        val productoRating = sharedPreferences.getFloat(productoRatingKey, 0.0f)
        val productoComment = sharedPreferences.getString(productoCommentKey, "")


        ratingBar.rating = productoRating
        commentEditText.setText(productoComment)


        // Asignar los valores a las vistas correspondientes
        val tituloTextView = findViewById<TextView>(R.id.producto_name)
        val precioTextView = findViewById<TextView>(R.id.producto_precio)
        val creacionTextView = findViewById<TextView>(R.id.producto_creacion)
        val descripcionTextView = findViewById<TextView>(R.id.producto_description)

        /**
         * /
        val rating = 4.5f
        sharedPreferences.edit().putFloat("productoRating_$productoId", rating).apply()

        val comentario = "¡Gran producto!"
        sharedPreferences.edit().putString("productoComment_$productoId", comentario).apply()

         */


        tituloTextView.text = productoNombre
        precioTextView.text = productoPrecio
        creacionTextView.text = productoCreation
        descripcionTextView.text = productoDescripcion


         val imagen1ImageView = findViewById<ImageView>(R.id.producto_image1)
         val imagen2ImageView = findViewById<ImageView>(R.id.producto_image2)
         val imagen3ImageView = findViewById<ImageView>(R.id.producto_image3)

        val images = intent.getStringArrayListExtra("productoImages")

        if (images != null) {
            if (images.size >= 3) {
                Picasso.get().load(images[0]).error(R.drawable.capture3).into(imagen1ImageView)
                Picasso.get().load(images[1]).error(R.drawable.capture3).into(imagen2ImageView)
                Picasso.get().load(images[2]).error(R.drawable.capture3).into(imagen3ImageView)
            } else {
                Picasso.get().load(R.drawable.capture3).into(imagen1ImageView)
                Picasso.get().load(R.drawable.capture3).into(imagen2ImageView)
                Picasso.get().load(R.drawable.capture3).into(imagen3ImageView)
            }
        } else {
            Picasso.get().load(R.drawable.capture3).into(imagen1ImageView)
            Picasso.get().load(R.drawable.capture3).into(imagen2ImageView)
            Picasso.get().load(R.drawable.capture3).into(imagen3ImageView)
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Guardar el nuevo valor en las preferencias compartidas
            sharedPreferences.edit().putFloat(productoRatingKey, rating).apply()
            }

        // Escuchar cambios en el comentario (EditText)
        commentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Guardar el nuevo comentario en las preferencias compartidas
                val nuevoComentario = s.toString()
                sharedPreferences.edit().putString(productoCommentKey, nuevoComentario).apply()
            }
        })


        }
}