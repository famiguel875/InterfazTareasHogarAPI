package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazloginregister.model.TareaDTO
import com.example.interfazloginregister.viewmodel.TareasViewModel

/**
 * Pantalla de Lista de Tareas.
 *
 * Esta función Composable muestra la lista de tareas obtenidas desde el ViewModel (TareasViewModel).
 * Se carga la lista de tareas al iniciarse la pantalla y se utiliza un Scaffold para estructurar la UI,
 * incluyendo una TopAppBar para navegación y refresco, y un botón flotante para agregar nuevas tareas.
 *
 * @param viewModel Instancia del TareasViewModel que gestiona la lógica de negocio y la conexión con la API.
 * @param navController Controlador de navegación que permite regresar al menú o moverse entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TareasViewModel, navController: NavController) {
    // Se recoge la lista de tareas del ViewModel a través de un StateFlow.
    val tareas by viewModel.tareas.collectAsState()
    // Variable mutable que controla la visibilidad del diálogo para agregar tareas.
    var showDialog by remember { mutableStateOf(false) }

    // Carga las tareas al iniciar la pantalla.
    LaunchedEffect(Unit) {
        viewModel.cargarTareas()
    }

    Scaffold(
        // Configuración de la TopAppBar con título, botón de navegación y refresco.
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tareas", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    // Botón para volver a la pantalla "menu".
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                    }
                },
                actions = {
                    // Botón para refrescar la lista de tareas.
                    IconButton(onClick = { viewModel.cargarTareas() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = Color.Black)
                    }
                }
            )
        },
        // Botón flotante para agregar una nueva tarea.
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Tarea", tint = Color.White)
            }
        }
    ) { paddingValues ->
        // Contenedor principal de la pantalla que muestra la lista o un mensaje si la lista está vacía.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (tareas.isEmpty()) {
                Text("No hay tareas disponibles", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            } else {
                // Se utiliza LazyColumn para mostrar cada tarea en la lista.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(tareas) { tarea ->
                        TaskItem(tarea, viewModel)
                    }
                }
            }
        }
    }

    // Muestra el diálogo para agregar una tarea cuando showDialog es true.
    if (showDialog) {
        AddTaskDialog(onDismiss = { showDialog = false }, viewModel = viewModel)
    }
}

/**
 * Composable que representa un ítem individual de tarea en la lista.
 *
 * Muestra la información básica de la tarea (título, usuario, descripción y estado).
 * Además, incluye un Checkbox para actualizar el estado de la tarea y un botón para eliminarla.
 *
 * @param tarea Objeto TareaDTO que contiene los datos de la tarea.
 * @param viewModel Instancia del TareasViewModel para invocar las funciones de actualización y eliminación.
 */
@Composable
fun TaskItem(tarea: TareaDTO, viewModel: TareasViewModel) {
    // Variable mutable que refleja si la tarea no está pendiente (estado diferente de "PENDIENTE").
    var isChecked by remember { mutableStateOf(tarea.estado != "PENDIENTE") }

    // Card que encapsula la información de la tarea.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Columna para organizar verticalmente los detalles de la tarea.
        Column(modifier = Modifier.padding(16.dp)) {
            // Muestra el título de la tarea.
            Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            // Muestra el nombre de usuario asociado a la tarea.
            Text(text = "Usuario: ${tarea.username}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            // Muestra la descripción de la tarea.
            tarea.descripcion.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            // Fila que organiza el Checkbox y el botón de eliminación.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Muestra el estado de la tarea.
                    Text("Estado: ${tarea.estado}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(8.dp))
                    // Checkbox para actualizar el estado de la tarea a "COMPLETADA".
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            viewModel.actualizarEstadoTarea(tarea.codigo, "COMPLETADA")
                        }
                    )
                }
                // Botón para eliminar la tarea.
                IconButton(onClick = { viewModel.eliminarTarea(tarea.codigo) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar tarea", tint = Color.Red)
                }
            }
        }
    }
}

/**
 * Diálogo para agregar una nueva tarea.
 *
 * Muestra campos de entrada para que el usuario ingrese los datos requeridos para crear una tarea:
 * código (en el formato TARxxx), título, descripción y usuario. Al confirmar, se llama a la función
 * [crearTarea] del ViewModel y se cierra el diálogo.
 *
 * @param onDismiss Función callback que se ejecuta cuando se cierra el diálogo.
 * @param viewModel Instancia del TareasViewModel que gestiona la creación de la tarea.
 */
@Composable
fun AddTaskDialog(onDismiss: () -> Unit, viewModel: TareasViewModel) {
    var codigo by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Nueva Tarea") },
        text = {
            Column {
                // Campo para ingresar el código de la tarea.
                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código (formato TARxxx)") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para ingresar el título de la tarea.
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para ingresar la descripción de la tarea.
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para ingresar el usuario asociado a la tarea.
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    singleLine = true
                )
            }
        },
        // Botón para confirmar la creación de la tarea.
        confirmButton = {
            Button(
                onClick = {
                    viewModel.crearTarea(codigo, titulo, descripcion, usuario)
                    onDismiss()
                }
            ) {
                Text("Agregar")
            }
        },
        // Botón para cancelar y cerrar el diálogo.
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

