package com.desafiolatam.restapi.consumo_API.clientes

import com.desafiolatam.restapi.model.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://api.escuelajs.co/api/v1/"
        fun retrofitInstance(): Api {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Api::class.java)
        }
    }

}


