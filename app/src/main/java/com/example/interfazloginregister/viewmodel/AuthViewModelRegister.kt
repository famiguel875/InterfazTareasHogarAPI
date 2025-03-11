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
 * Este ViewModel gestiona la lógica de registro de nuevos usuarios. Utiliza el ApiService para
 * enviar los datos de registro a la API y UserPreferences para gestionar la persistencia (aunque en este
 * caso no se manipula el token). Se actualiza un indicador de carga y se capturan posibles errores.
 *
 * @param api Instancia de ApiService que define el endpoint para registrar usuarios.
 * @param userPreferences Instancia de UserPreferences para acceder a la persistencia de datos (p.ej., para almacenar un token en otros casos).
 */
class AuthViewModelRegister(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // StateFlow para controlar el estado de carga durante el registro.
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    // StateFlow para manejar y mostrar mensajes de error en el proceso de registro.
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Registra un nuevo usuario.
     *
     * Esta función recibe un objeto UsuarioRegisterDTO que contiene los datos necesarios para el registro
     * (username, email, password, passwordRepeat y rol). Se realiza la llamada al endpoint correspondiente
     * a través de ApiService. Si ocurre un error durante la llamada, se captura y se actualiza el StateFlow de error.
     *
     * @param user Objeto de tipo UsuarioRegisterDTO con los datos del usuario a registrar.
     */
    fun register(user: UsuarioRegisterDTO) {
        viewModelScope.launch {
            _loading.value = true  // Se activa el indicador de carga.
            _error.value = null      // Se limpia cualquier error anterior.
            try {
                // Llamada al endpoint de registro. Si se produce un error, se captura en el bloque catch.
                api.registerUser(user)
            } catch (e: Exception) {
                // Se actualiza el StateFlow de error con el mensaje de la excepción.
                _error.value = "Error en el registro: ${e.message}"
            }
            _loading.value = false  // Se desactiva el indicador de carga al finalizar.
        }
    }
}



