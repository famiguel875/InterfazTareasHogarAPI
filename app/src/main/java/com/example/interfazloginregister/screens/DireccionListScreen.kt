package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazloginregister.model.Direccion
import com.example.interfazloginregister.viewmodel.DireccionesViewModel

/**
 * Pantalla principal que muestra la lista de direcciones.
 *
 * Esta función Composable utiliza el [DireccionesViewModel] para obtener la lista
 * de direcciones y actualizarlas a través de llamadas a la API. Además, permite la
 * navegación de vuelta al menú y la apertura de un diálogo para agregar nuevas direcciones.
 *
 * @param viewModel Instancia del ViewModel encargado de gestionar las direcciones.
 * @param navController Controlador de navegación para gestionar la navegación entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DireccionListScreen(viewModel: DireccionesViewModel, navController: NavController) {
    // Se obtiene la lista de direcciones desde el StateFlow del ViewModel.
    val direcciones by viewModel.direccionesList.collectAsState()
    // Variable mutable para controlar si se muestra el diálogo para agregar dirección.
    var showDialog by remember { mutableStateOf(false) }

    // Al iniciar la pantalla se carga la lista de direcciones llamando al ViewModel.
    LaunchedEffect(Unit) {
        viewModel.cargarDirecciones()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de Direcciones", style = MaterialTheme.typography.headlineMedium)
                },
                // Botón de navegación para volver a la pantalla de menú.
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                    }
                },
                // Botón para refrescar la lista de direcciones.
                actions = {
                    IconButton(onClick = { viewModel.cargarDirecciones() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = Color.Black)
                    }
                }
            )
        },
        // Botón flotante para abrir el diálogo de agregar dirección.
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Dirección", tint = Color.White)
            }
        }
    ) { paddingValues ->
        // Contenedor principal que muestra la lista o un mensaje si la lista está vacía.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (direcciones.isEmpty()) {
                Text("No hay direcciones disponibles", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            } else {
                // Se utiliza LazyColumn para listar cada dirección.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(direcciones) { direccion ->
                        DireccionItem(direccion, viewModel)
                    }
                }
            }
        }
    }

    // Muestra el diálogo para agregar dirección si showDialog es verdadero.
    if (showDialog) {
        AddDireccionDialog(onDismiss = { showDialog = false }, viewModel = viewModel)
    }
}

/**
 * Composable que representa un ítem de la lista de direcciones.
 *
 * Muestra la información de una dirección (calle, número, ciudad, código postal y username).
 * Además, incluye un botón para eliminar la dirección llamando al ViewModel.
 *
 * @param direccion Objeto [Direccion] que contiene los datos de la dirección.
 * @param viewModel Instancia del [DireccionesViewModel] para ejecutar operaciones.
 */
@Composable
fun DireccionItem(direccion: Direccion, viewModel: DireccionesViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Muestra la calle y el número.
            Text(text = "${direccion.calle} ${direccion.numero}", style = MaterialTheme.typography.titleMedium)
            // Muestra la ciudad y el código postal.
            Text(text = "${direccion.ciudad} (${direccion.codigoPostal})", style = MaterialTheme.typography.bodyMedium)
            // Muestra el username asociado a la dirección.
            Text(text = "Usuario: ${direccion.username}", style = MaterialTheme.typography.bodyMedium)
            // Botón para eliminar la dirección.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { viewModel.deleteDireccion(direccion.codigo) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar dirección", tint = Color.Red)
                }
            }
        }
    }
}

/**
 * Diálogo para agregar una nueva dirección.
 *
 * Muestra campos de entrada para que el usuario ingrese los datos necesarios para
 * crear una nueva dirección (código, calle, número, ciudad, código postal y username).
 * Al confirmar, se llama a la función [crearDireccion] del ViewModel.
 *
 * @param onDismiss Función a ejecutar cuando se cierra el diálogo.
 * @param viewModel Instancia del [DireccionesViewModel] para gestionar la creación de la dirección.
 */
@Composable
fun AddDireccionDialog(onDismiss: () -> Unit, viewModel: DireccionesViewModel) {
    var codigo by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Nueva Dirección") },
        text = {
            Column {
                // Campo para el código de la dirección (formato DIRxxx).
                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código (formato DIRxxx)") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para la calle.
                OutlinedTextField(
                    value = calle,
                    onValueChange = { calle = it },
                    label = { Text("Calle") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para el número.
                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para la ciudad.
                OutlinedTextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para el código postal.
                OutlinedTextField(
                    value = codigoPostal,
                    onValueChange = { codigoPostal = it },
                    label = { Text("Código Postal") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo para el username asociado a la dirección.
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Se llama a la función del ViewModel para crear la dirección.
                viewModel.crearDireccion(codigo, calle, numero, ciudad, codigoPostal, username)
                onDismiss()
            }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}




