package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.TareaDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de tareas.
 *
 * Este ViewModel se encarga de la lógica de negocio relacionada con las tareas, utilizando Retrofit
 * para comunicarse con la API y UserPreferences para obtener el token de autenticación (JWT). Se
 * actualiza la lista de tareas a través de un StateFlow, y se proporcionan métodos para cargar,
 * actualizar, eliminar y crear tareas.
 *
 * @param api Instancia de ApiService que define los endpoints para la gestión de tareas.
 * @param userPreferences Instancia de UserPreferences para obtener el token JWT persistido.
 */
class TareasViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // StateFlow que almacena la lista de tareas (utilizando TareaDTO para la comunicación con la API).
    private val _tareas = MutableStateFlow<List<TareaDTO>>(emptyList())
    val tareas: StateFlow<List<TareaDTO>> get() = _tareas

    // Variable que almacena el token JWT obtenido desde UserPreferences.
    var token: String? = null

    // Se observa el token almacenado en UserPreferences para inicializar la carga de tareas.
    init {
        viewModelScope.launch {
            userPreferences.authToken.collect { storedToken ->
                token = storedToken
                if (storedToken != null) {
                    cargarTareas()
                }
            }
        }
    }

    /**
     * Carga la lista de tareas.
     *
     * Este método realiza una llamada al endpoint getAllTareas utilizando el token JWT (con el prefijo "Bearer")
     * y actualiza el StateFlow _tareas con la lista de tareas obtenida. Si ocurre algún error, se imprime
     * el mensaje en la consola.
     */
    fun cargarTareas() {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se pasa el token con el prefijo "Bearer" a la llamada de la API.
                    val response = api.getAllTareas("Bearer $tkn")
                    _tareas.value = response
                }
            } catch (e: Exception) {
                println("Error al cargar tareas: ${e.message}")
            }
        }
    }

    /**
     * Actualiza el estado de una tarea.
     *
     * Llama al endpoint completarTarea, que fija el estado de la tarea a "COMPLETADA" según la lógica del servidor.
     * Después de la actualización, se vuelve a cargar la lista de tareas.
     *
     * @param codigo Código único de la tarea (formato TARxxx).
     * @param nuevoEstado Nuevo estado de la tarea (aunque el endpoint fija el estado a "COMPLETADA").
     */
    fun actualizarEstadoTarea(codigo: String, nuevoEstado: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se llama al endpoint para completar la tarea.
                    api.completarTarea("Bearer $tkn", codigo)
                    // Se refresca la lista de tareas.
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al actualizar tarea: ${e.message}")
            }
        }
    }

    /**
     * Elimina una tarea.
     *
     * Llama al endpoint deleteTarea para eliminar la tarea identificada por su código.
     * Luego, se vuelve a cargar la lista de tareas para actualizar la interfaz.
     *
     * @param codigo Código único de la tarea a eliminar (formato TARxxx).
     */
    fun eliminarTarea(codigo: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    api.deleteTarea("Bearer $tkn", codigo)
                    // Se refresca la lista de tareas tras la eliminación.
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al eliminar tarea: ${e.message}")
            }
        }
    }

    /**
     * Crea una nueva tarea.
     *
     * Se requiere enviar el código manualmente, junto con título, descripción y el usuario propietario.
     * Se crea el objeto TareaDTO fuera del bloque launch, siguiendo el mismo patrón que en la creación
     * de direcciones. Posteriormente, se llama al endpoint createTarea y se añade la tarea creada a la lista,
     * refrescando la misma.
     *
     * @param codigo Código de la tarea (formato TARxxx).
     * @param titulo Título de la tarea.
     * @param descripcion Descripción de la tarea.
     * @param usuario Usuario propietario de la tarea.
     */
    fun crearTarea(codigo: String, titulo: String, descripcion: String, usuario: String) {
        // Creación del objeto TareaDTO fuera del bloque launch.
        val nuevaTarea = TareaDTO(
            id = null,
            codigo = codigo,
            titulo = titulo,
            descripcion = descripcion,
            estado = "PENDIENTE",
            username = usuario
        )
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se envía la nueva tarea a la API.
                    val response = api.createTarea("Bearer $tkn", nuevaTarea)
                    // Se añade la tarea creada a la lista actual.
                    _tareas.value += response
                    // Se refresca la lista de tareas para actualizar la UI.
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al crear tarea: ${e.message}")
            }
        }
    }
}







