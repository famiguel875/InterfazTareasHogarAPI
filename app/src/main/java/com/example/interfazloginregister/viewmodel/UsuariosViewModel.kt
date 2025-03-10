package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.Usuario
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> get() = _usuario

    private val _usuariosList = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosList: StateFlow<List<Usuario>> get() = _usuariosList

    var token: String? = null

    init {
        viewModelScope.launch {
            userPreferences.authToken.collect { storedToken ->
                token = storedToken
                if (storedToken != null) {
                    cargarUsuarios()
                }
            }
        }
    }

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

    fun deleteUsuario(username: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    _usuario.value = api.deleteUsuario("Bearer $tkn", username)
                    // Tras eliminar, refrescamos la lista
                    cargarUsuarios()
                }
            } catch (e: Exception) {
                println("Error al eliminar usuario: ${e.message}")
            }
        }
    }
}

