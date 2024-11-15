package com.progress.photos.delivery.dataclass

data class User(
    val authToken: String? = null,
    val email: String,
    val name: String,
    val password : String? = null,
    val phone : String? = null,
    val userId: Int? = null
)