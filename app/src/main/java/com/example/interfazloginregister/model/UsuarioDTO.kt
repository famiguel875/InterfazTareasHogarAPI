package com.example.interfazloginregister.model

import java.time.LocalDateTime

data class UsuarioDTO(
    val id: String?,
    val username: String,
    val email: String,
    val roles: String,            // Ahora es un String (por ejemplo: "USER" o "ADMIN")
    val fechaRegistro: LocalDateTime
)