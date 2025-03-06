package com.example.interfazloginregister.model

data class UsuarioDTO(
    val id: String?,
    val username: String,
    val email: String,
    val roles: List<String>,
    val direccion: Direccion?
)