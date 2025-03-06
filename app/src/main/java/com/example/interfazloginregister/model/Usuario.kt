package com.example.interfazloginregister.model

data class Usuario(
    val id: String?,
    val username: String,
    val email: String,
    val roles: List<String>,
    val direccion: Direccion?
)