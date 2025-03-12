package com.example.interfazloginregister.model

data class Usuario(
    val id: String?,
    val username: String,
    val password: String,  // Idealmente se omite el password en la respuesta.
    val roles: String?,
    val email: String?,
    val direccion: Direccion?// Ahora es un String (por ejemplo: "USER" o "ADMIN")

)

