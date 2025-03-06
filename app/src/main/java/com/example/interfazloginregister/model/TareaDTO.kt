package com.example.interfazloginregister.model

class TareaDTO(
    val id: String? = null,
    val codigo: String, // Código secundario: formato TARxxx (ej. "TAR001")
    val titulo: String,
    val descripcion: String,
    var estado: String, // Ej.: "PENDIENTE" o "COMPLETADA"
    val username: String // Almacena el username del propietario
)