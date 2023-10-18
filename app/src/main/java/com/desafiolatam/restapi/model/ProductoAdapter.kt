package com.desafiolatam.restapi.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.R
import com.desafiolatam.restapi.consumo_API.pojo.Categoria
import com.desafiolatam.restapi.consumo_API.pojo.Producto
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class ProductoAdapter(private val myDataset: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {


    class ProductoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var productoImage : ImageView = itemView.findViewById(R.id.producto_image)
        var productoName: TextView = itemView.findViewById(R.id.producto_name)
        var productoPrecio: TextView = itemView.findViewById(R.id.producto_precio)
        var productoCreacion: TextView = itemView.findViewById(R.id.producto_creacion)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.productos_list_item, parent, false)
        return ProductoHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        val producto = myDataset[position]
        Picasso.get()
            .load(producto.images[0])
            .placeholder(R.drawable.ic_launcher_background)
            .error(androidx.appcompat.R.drawable.abc_btn_check_material)
            .into(holder.productoImage, object : Callback {
                override fun onSuccess() {
                    Log.i("Picasso", "Imagen cargada exitosamente")
                }
                override fun onError(e: Exception?) {
                    Log.e("Picasso", "Error al cargar la imagen: ${e?.message}")
                }
            })

        Log.i("Productos Links",producto.images[0])

        holder.productoName.text = "Title: " + producto.title
        holder.productoPrecio.text = "$ " + producto.price.toString()


        val fechaOriginal = producto.creationAt
        val formatoOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        try {
            val fecha = formatoOriginal.parse(fechaOriginal)
            val formatoNuevo = SimpleDateFormat("dd-MM-yyyy")
            val fechaFormateada = formatoNuevo.format(fecha)
            holder.productoCreacion.text = "Fecha: $fechaFormateada"
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}