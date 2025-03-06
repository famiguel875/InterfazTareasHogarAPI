package com.example.interfazloginregister.model

data class Tarea(
    val id: String? = null,
    val codigo: String, // Código secundario: formato TARxxx (ej. "TAR001")
    val titulo: String,
    val descripcion: String,
    var estado: String, // Ej.: "PENDIENTE" o "COMPLETADA"
    val username: String // Almacena el username del propietario
)