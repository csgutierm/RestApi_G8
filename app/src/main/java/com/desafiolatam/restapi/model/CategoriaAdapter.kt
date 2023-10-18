package com.desafiolatam.restapi.model

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.R
import com.desafiolatam.restapi.consumo_API.pojo.Categoria

class CategoriaAdapter(private val myDataset: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder>() {


    class CategoriaHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        //var photoImage : ImageView = itemView.findViewById(R.id.photo_image)
        //var photoTitle: TextView = itemView.findViewById(R.id.photo_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        TODO("Not yet implemented")
    }
}