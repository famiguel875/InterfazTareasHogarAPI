package com.example.interfazloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.TareaDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TareasViewModel(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _tareas = MutableStateFlow<List<TareaDTO>>(emptyList())
    val tareas: StateFlow<List<TareaDTO>> get() = _tareas

    var token: String? = null

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

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Se pasa el token con el prefijo "Bearer "
                    val response = api.getAllTareas("Bearer $tkn")
                    _tareas.value = response
                }
            } catch (e: Exception) {
                println("Error al cargar tareas: ${e.message}")
            }
        }
    }

    fun actualizarEstadoTarea(codigo: String, nuevoEstado: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    // Según el controlador, el endpoint completarTarea fija el estado a "COMPLETADA"
                    api.completarTarea("Bearer $tkn", codigo)
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al actualizar tarea: ${e.message}")
            }
        }
    }

    fun eliminarTarea(codigo: String) {
        viewModelScope.launch {
            try {
                token?.let { tkn ->
                    api.deleteTarea("Bearer $tkn", codigo)
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al eliminar tarea: ${e.message}")
            }
        }
    }

    /**
     * Crea una nueva tarea.
     * Se requiere enviar el código manualmente, junto con título, descripción y usuario.
     * Se crea el objeto TareaDTO fuera del bloque launch, siguiendo el mismo patrón de crearDirección.
     */
    fun crearTarea(codigo: String, titulo: String, descripcion: String, usuario: String) {
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
                    val response = api.createTarea("Bearer $tkn", nuevaTarea)
                    // Se añade la tarea creada a la lista y se refresca la misma.
                    _tareas.value += response
                    cargarTareas()
                }
            } catch (e: Exception) {
                println("Error al crear tarea: ${e.message}")
            }
        }
    }
}






