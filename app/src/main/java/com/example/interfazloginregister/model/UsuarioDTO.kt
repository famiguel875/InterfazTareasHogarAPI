package com.example.interfazloginregister.model

data class UsuarioDTO(
    val id: String?,
    val username: String,
    val password: String,
    val roles: String?,
    val email: String?,
    val direccion: Direccion?// Ahora es un String (por ejemplo: "USER" o "ADMIN"
)