package com.desafiolatam.restapi.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class PhotoAdapter(private val myDataset: List<Photo>) :

    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PhotoHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photos_list_item, parent, false)
        return PhotoHolder(view)
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo = myDataset[position]
        //holder.photoImage.text = photo.url
        Picasso.get()
            .load(photo.url)
            //.load("https://i.imgur.com/DvpvklR.png")
            //.load(R.drawable.capture3)
            .placeholder(R.drawable.ic_launcher_background)
            .error(androidx.appcompat.R.drawable.abc_btn_check_material)
            .into(holder.photoImage, object : Callback {
                override fun onSuccess() {
                    Log.i("Picasso", "Imagen cargada exitosamente")
                }

                override fun onError(e: Exception?) {
                    Log.e("Picasso", "Error al cargar la imagen: ${e?.message}")
                }
            })

        Log.i("Photos Links",photo.url)

        holder.photoTitle.text = photo.title

    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var photoImage : ImageView = itemView.findViewById(R.id.photo_image )
        var photoTitle: TextView = itemView.findViewById(R.id.photo_title)
    }
}