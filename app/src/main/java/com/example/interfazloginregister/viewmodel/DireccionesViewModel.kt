package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.Direccion
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de direcciones.
 *
 * Este ViewModel se encarga de gestionar la lógica relacionada con las direcciones, como
 * cargar, obtener por código o usuario, crear, actualizar y eliminar direcciones.
 *
 * @param api Instancia de ApiService que define los endpoints para direcciones.
 * @param userPreferences Instancia de UserPreferences para acceder y gestionar el token de autenticación.
 */
class DireccionesViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // StateFlow que almacena una dirección individual (última consultada, creada o actualizada).
    private val _direccion = MutableStateFlow<Direccion?>(null)
    val direccion: StateFlow<Direccion?> get() = _direccion

    // StateFlow que almacena la lista de direcciones.
    private val _direccionesList = MutableStateFlow<List<Direccion>>(emptyList())
    val direccionesList: StateFlow<List<Direccion>> get() = _direccionesList

    // Variable para almacenar el token de autenticación obtenido desde UserPreferences.
    var token: String? = null

    // Inicialización: Se observa el token almacenado en UserPreferences y, si es válido,
    // se carga la lista de direcciones.
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

    /**
     * Carga la lista de direcciones.
     *
     * Esta función llama al endpoint getAllDirecciones de la API usando el token (formateado con "Bearer ")
     * y actualiza el StateFlow de la lista de direcciones.
     */
    fun cargarDirecciones() {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se realiza la llamada a la API para obtener la lista de direcciones.
                    val response = api.getAllDirecciones("Bearer $tkn")
                    _direccionesList.value = response
                }
            } catch (e: Exception) {
                println("Error al cargar direcciones: ${e.message}")
            }
        }
    }

    /**
     * Crea una nueva dirección.
     *
     * Se crea un objeto Direccion con los datos proporcionados y se envía al endpoint createDireccion.
     * Tras la creación, se actualiza el StateFlow de la dirección individual y se refresca la lista.
     *
     * @param codigo Código de la dirección (formato DIRxxx).
     * @param calle Calle de la dirección.
     * @param numero Número de la dirección.
     * @param ciudad Ciudad.
     * @param codigoPostal Código postal.
     * @param username Usuario al que se asigna la dirección.
     */
    fun crearDireccion(
        codigo: String,
        calle: String,
        numero: String,
        ciudad: String,
        codigoPostal: String,
        username: String
    ) {
        // Se crea el objeto Direccion fuera del bloque launch.
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
                    // Se envía la nueva dirección a la API.
                    val response = api.createDireccion("Bearer $tkn", nuevaDireccion)
                    _direccion.value = response
                    cargarDirecciones()  // Refresca la lista de direcciones.
                }
            } catch (e: Exception) {
                println("Error al crear dirección: ${e.message}")
            }
        }
    }

    /**
     * Elimina una dirección.
     *
     * Llama al endpoint deleteDireccion de la API usando el código de la dirección. Tras la eliminación,
     * refresca la lista de direcciones.
     *
     * @param codigo Código de la dirección a eliminar.
     */
    fun deleteDireccion(codigo: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se realiza la eliminación de la dirección a través de la API.
                    val response = api.deleteDireccion("Bearer $tkn", codigo)
                    _direccion.value = response
                    cargarDirecciones()  // Refresca la lista.
                }
            } catch (e: Exception) {
                println("Error al eliminar dirección: ${e.message}")
            }
        }
    }
}