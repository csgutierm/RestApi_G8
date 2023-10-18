package com.desafiolatam.restapi.consumo_API.pojo

data class Producto(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val images: List<String>,
    val creationAt: String,
    val updatedAt: String,
    val categoryId: Int
)
