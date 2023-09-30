package com.desafiolatam.restapi.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.restapi.R

class UserAdapter(private val myDataset: List<User>) :

    RecyclerView.Adapter<UserAdapter.UserHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            UserHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_list_item, parent, false)
        return UserHolder(view)
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = myDataset[position]
        //holder.id.text = user.id.toString()
        //holder.username.text = user.username
        holder.name.text = user.name
        holder.email.text = user.email

        //sanitizando los números
        val phoneNumber = user.phone
        val indexOfX = phoneNumber.indexOf('x')
        val cleanedPhoneNumber = if (indexOfX != -1) phoneNumber.substring(0, indexOfX) else phoneNumber
        holder.phone.text = cleanedPhoneNumber

        //holder.phone.text = user.phone
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        //var id: TextView = itemView.findViewById(R.id.tvId)
        var name: TextView = itemView.findViewById(R.id.name)
        //var username: TextView = itemView.findViewById(R.id.username)
        var email: TextView = itemView.findViewById(R.id.email)
        var phone: TextView = itemView.findViewById(R.id.phone)
    }
}