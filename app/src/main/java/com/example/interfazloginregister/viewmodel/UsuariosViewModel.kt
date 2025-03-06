package com.example.interfazloginregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.Tarea
import com.example.interfazloginregister.model.Usuario
import com.example.interfazloginregister.model.UsuarioDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.network.RetrofitInstance
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // Estado para un usuario individual (último consultado, actualizado, etc.)
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> get() = _usuario

    // Estado para la lista de usuarios
    private val _usuariosList = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosList: StateFlow<List<Usuario>> get() = _usuariosList

    // Token obtenido (se actualiza desde UserPreferences)
    var token: String? = null

    init {
        viewModelScope.launch {
            userPreferences.authToken.collect { storedToken ->
                token = storedToken
                if (storedToken != null) {
                    cargarUsuarios() // Cargar la lista de usuarios (generalmente para ADMIN)
                }
            }
        }
    }

    // Carga todos los usuarios (restringido a ADMIN en el servidor)
    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.getAllUsuarios("Bearer $tkn")
                    _usuariosList.value = response
                }
            } catch (e: Exception) {
                println("Error al cargar usuarios: ${e.message}")
            }
        }
    }

    // Obtiene la información de un usuario específico por username
    fun getUsuarioByUsername(username: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.getUsuarioByUsername("Bearer $tkn", username)
                    _usuario.value = response
                }
            } catch (e: Exception) {
                println("Error al obtener usuario: ${e.message}")
            }
        }
    }

    // Actualiza la información de un usuario
    fun updateUsuario(username: String, usuario: Usuario) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.updateUsuario("Bearer $tkn", username, usuario)
                    _usuario.value = response
                }
            } catch (e: Exception) {
                println("Error al actualizar usuario: ${e.message}")
            }
        }
    }

    // Elimina un usuario
    fun deleteUsuario(username: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    val response = api.deleteUsuario("Bearer $tkn", username)
                    _usuario.value = response
                }
            } catch (e: Exception) {
                println("Error al eliminar usuario: ${e.message}")
            }
        }
    }
}



