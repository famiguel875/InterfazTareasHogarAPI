package com.example.interfazloginregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.Direccion
import com.example.interfazloginregister.model.DireccionDTO
import com.example.interfazloginregister.model.Tarea
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.network.RetrofitInstance
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DireccionesViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // Estado para una dirección individual
    private val _direccion = MutableStateFlow<Direccion?>(null)
    val direccion: StateFlow<Direccion?> get() = _direccion

    // Estado para la lista de direcciones
    private val _direccionesList = MutableStateFlow<List<Direccion>>(emptyList())
    val direccionesList: StateFlow<List<Direccion>> get() = _direccionesList

    // Token obtenido (se actualiza desde UserPreferences)
    var token: String? = null

    init {
        viewModelScope.launch {
            userPreferences.authToken.collect { storedToken ->
                token = storedToken
                if (storedToken != null) {
                    cargarDirecciones()
                }
            }
        }
    }

    fun cargarDirecciones() {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.getAllDirecciones("Bearer $tkn")
                    _direccionesList.value = response
                }
            } catch (e: Exception) {
                println("Error al cargar direcciones: ${e.message}")
            }
        }
    }

    fun getDireccionByCodigo(codigo: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.getDireccionByCodigo("Bearer $tkn", codigo)
                    _direccion.value = response
                }
            } catch (e: Exception) {
                println("Error al obtener dirección por código: ${e.message}")
            }
        }
    }

    fun getDireccionByUsuario(username: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.getDireccionByUsuario("Bearer $tkn", username)
                    _direccion.value = response
                }
            } catch (e: Exception) {
                println("Error al obtener dirección por usuario: ${e.message}")
            }
        }
    }

    fun crearDireccion(
        codigo: String,
        calle: String,
        numero: String,
        ciudad: String,
        codigoPostal: String,
        username: String
    ) {
        // Se crea el objeto Direccion fuera del bloque launch
        val nuevaDireccion = Direccion(
            id = null,
            codigo = codigo,
            calle = calle,
            numero = numero,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            username = username
        )
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.createDireccion("Bearer $tkn", nuevaDireccion)
                    _direccion.value = response
                    cargarDirecciones()  // Refresca la lista
                }
            } catch (e: Exception) {
                println("Error al crear dirección: ${e.message}")
            }
        }
    }

    fun updateDireccion(codigo: String, direccion: Direccion) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.updateDireccion("Bearer $tkn", codigo, direccion)
                    _direccion.value = response
                    cargarDirecciones()  // Refresca la lista
                }
            } catch (e: Exception) {
                println("Error al actualizar dirección: ${e.message}")
            }
        }
    }

    fun deleteDireccion(codigo: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.deleteDireccion("Bearer $tkn", codigo)
                    _direccion.value = response
                    cargarDirecciones()  // Refresca la lista
                }
            } catch (e: Exception) {
                println("Error al eliminar dirección: ${e.message}")
            }
        }
    }
}






