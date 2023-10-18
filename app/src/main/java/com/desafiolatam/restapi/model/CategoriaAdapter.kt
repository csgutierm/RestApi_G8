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
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CategoriaAdapter(private val myDataset: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder>() {


    class CategoriaHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var categoriaImage : ImageView = itemView.findViewById(R.id.categoria_image)
        var categoriaName: TextView = itemView.findViewById(R.id.categoria_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categorias_list_item, parent, false)
        return CategoriaHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        val categoria = myDataset[position]
        Picasso.get()
            .load(categoria.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(androidx.appcompat.R.drawable.abc_btn_check_material)
            .into(holder.categoriaImage, object : Callback {
                override fun onSuccess() {
                    Log.i("Picasso", "Imagen cargada exitosamente")
                }
                override fun onError(e: Exception?) {
                    Log.e("Picasso", "Error al cargar la imagen: ${e?.message}")
                }
            })

        Log.i("Categorias Links",categoria.image)

        holder.categoriaName.text = categoria.name
    }
}