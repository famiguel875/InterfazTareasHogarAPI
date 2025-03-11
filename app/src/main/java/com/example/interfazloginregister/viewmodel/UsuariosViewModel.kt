package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.Usuario
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de usuarios.
 *
 * Este ViewModel se encarga de gestionar las operaciones relacionadas con usuarios, como cargar
 * la lista completa de usuarios y eliminar un usuario. Se utiliza ApiService para realizar las llamadas
 * a la API y UserPreferences para obtener y observar el token JWT de autenticación.
 *
 * @param api Instancia de ApiService que define los endpoints para usuarios.
 * @param userPreferences Instancia de UserPreferences que provee el token JWT almacenado.
 */
class UsuariosViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // StateFlow que almacena un usuario individual (último consultado, actualizado o eliminado).
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> get() = _usuario

    // StateFlow que almacena la lista completa de usuarios.
    private val _usuariosList = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosList: StateFlow<List<Usuario>> get() = _usuariosList

    // Variable que almacena el token JWT, obtenido desde UserPreferences.
    var token: String? = null

    // Inicialización: Se observa el flujo authToken de UserPreferences.
    // Cuando se obtiene un token válido, se llama a cargarUsuarios() para obtener la lista de usuarios.
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

    /**
     * Carga la lista de usuarios.
     *
     * Este método se conecta al endpoint getAllUsuarios de la API utilizando el token JWT (con el prefijo "Bearer")
     * y actualiza el StateFlow _usuariosList con la lista de usuarios obtenida. Se manejan excepciones
     * imprimiendo el error en consola.
     */
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

    /**
     * Elimina un usuario.
     *
     * Llama al endpoint deleteUsuario de la API utilizando el token JWT y el username del usuario a eliminar.
     * Después de la eliminación, se refresca la lista de usuarios.
     *
     * @param username El nombre de usuario del usuario que se desea eliminar.
     */
    fun deleteUsuario(username: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se actualiza el StateFlow _usuario con la respuesta de la API.
                    _usuario.value = api.deleteUsuario("Bearer $tkn", username)
                    // Tras eliminar el usuario, se vuelve a cargar la lista completa de usuarios.
                    cargarUsuarios()
                }
            } catch (e: Exception) {
                println("Error al eliminar usuario: ${e.message}")
            }
        }
    }
}