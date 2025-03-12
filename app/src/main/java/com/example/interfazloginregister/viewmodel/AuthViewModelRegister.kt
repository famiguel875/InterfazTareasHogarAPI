package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.UsuarioRegisterDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para el registro de usuarios.
 *
 * Este ViewModel gestiona la lógica de registro de nuevos usuarios.
 * Utiliza el ApiService para enviar los datos de registro a la API y
 * UserPreferences para gestionar la persistencia de datos (por ejemplo, almacenar el token en otros casos).
 *
 * Se emplea un MutableStateFlow para controlar el estado de carga (_loading) y otro para capturar
 * los mensajes de error (_error) durante el proceso.
 *
 * @param api Instancia de ApiService que define el endpoint para registrar usuarios.
 * @param userPreferences Instancia de UserPreferences para acceder a la persistencia de datos.
 */
class AuthViewModelRegister(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun register(user: UsuarioRegisterDTO) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                api.registerUser(user)
            } catch (e: Exception) {
                _error.value = "Error en el registro: ${e.message}"
            }
            _loading.value = false
        }
    }
}




