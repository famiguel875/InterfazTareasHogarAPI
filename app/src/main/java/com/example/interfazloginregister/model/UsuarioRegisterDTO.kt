package com.example.interfazloginregister.model

data class UsuarioRegisterDTO(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String?  // Si no se envía, se asigna por defecto en el servicio
)