package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.LoginUsuarioDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para el inicio de sesión (Login).
 *
 * Este ViewModel se encarga de gestionar la autenticación del usuario. Utiliza Retrofit para
 * comunicarse con la API, y UserPreferences para almacenar el token JWT de forma persistente.
 * La función login envía las credenciales del usuario y, si la autenticación es exitosa,
 * almacena el token y actualiza el estado correspondiente.
 *
 * @param api Instancia de ApiService que define los endpoints de la API.
 * @param userPreferences Instancia de UserPreferences para gestionar la persistencia del token.
 */
class AuthViewModelLogin(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // StateFlow para almacenar el token JWT; se actualiza tras un login exitoso.
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> get() = _token

    // StateFlow para controlar el estado de carga (por ejemplo, mostrar un indicador de progreso).
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    // StateFlow para manejar y mostrar mensajes de error durante el proceso de autenticación.
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Realiza la autenticación del usuario enviando las credenciales a la API.
     *
     * Este método utiliza el método loginUser del ApiService para enviar un objeto LoginUsuarioDTO
     * con el nombre de usuario y la contraseña. Si la respuesta contiene un token no vacío, se guarda
     * el token utilizando UserPreferences y se actualiza el StateFlow correspondiente. En caso de error,
     * se actualiza el StateFlow de error con un mensaje.
     *
     * @param username El nombre de usuario ingresado.
     * @param password La contraseña ingresada.
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loading.value = true  // Se activa el indicador de carga.
            _error.value = null      // Se limpia cualquier error previo.
            try {
                // Se realiza la llamada al endpoint de login utilizando las credenciales.
                val response = api.loginUser(LoginUsuarioDTO(username, password))
                // Si el token retornado no está vacío, se guarda y se actualiza el estado.
                if (response.token.isNotEmpty()) {
                    userPreferences.saveAuthToken(response.token)
                    _token.value = response.token
                } else {
                    _error.value = "Token inválido"
                }
            } catch (e: Exception) {
                // Se actualiza el error en caso de excepción durante la autenticación.
                _error.value = "Error de autenticación"
            }
            _loading.value = false  // Se desactiva el indicador de carga.
        }
    }
}


