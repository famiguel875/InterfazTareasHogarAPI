package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazloginregister.model.Usuario
import com.example.interfazloginregister.viewmodel.UsuariosViewModel

/**
 * Pantalla de Lista de Usuarios.
 *
 * Esta función Composable muestra la lista de usuarios obtenida desde el UsuariosViewModel.
 * Al iniciar la pantalla, se carga la lista de usuarios mediante la función cargarUsuarios().
 * Se utiliza un Scaffold para estructurar la interfaz, con una TopAppBar que incluye:
 *  - Un botón de navegación para volver al menú principal.
 *  - Un botón de refresco para actualizar la lista de usuarios.
 *
 * @param viewModel Instancia de UsuariosViewModel que gestiona el estado y las operaciones CRUD para usuarios.
 * @param navController Controlador de navegación para gestionar la transición entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UsuariosViewModel, navController: NavController) {
    // Se recoge la lista de usuarios del StateFlow del ViewModel.
    val usuarios by viewModel.usuariosList.collectAsState()

    // Se llama a cargarUsuarios() al iniciar la pantalla para obtener los datos.
    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Scaffold(
        // Definición de la TopAppBar del Scaffold.
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de Usuarios", style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    // Botón de navegación para volver al menú principal.
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                    }
                },
                actions = {
                    // Botón para refrescar la lista de usuarios.
                    IconButton(onClick = { viewModel.cargarUsuarios() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = Color.Black)
                    }
                }
            )
        }
    ) { paddingValues ->
        // Box contenedor principal que centra su contenido.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Si la lista está vacía, muestra un mensaje.
            if (usuarios.isEmpty()) {
                Text("No hay usuarios disponibles", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            } else {
                // LazyColumn para listar los usuarios.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(usuarios) { usuario ->
                        // Se muestra cada usuario usando el composable UserItem.
                        UserItem(usuario = usuario, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

/**
 * Composable para mostrar un ítem individual de usuario.
 *
 * Este composable representa visualmente los detalles de un usuario, mostrando su username,
 * email y roles. Además, incluye un botón que permite eliminar el usuario llamando a deleteUsuario()
 * del ViewModel.
 *
 * @param usuario Objeto Usuario que contiene la información a mostrar.
 * @param viewModel Instancia de UsuariosViewModel que provee la función para eliminar el usuario.
 */
@Composable
fun UserItem(usuario: Usuario, viewModel: UsuariosViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        // Se configura el color de fondo de la Card utilizando el esquema de colores de Material.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Se utiliza una Column para organizar verticalmente los datos del usuario.
        Column(modifier = Modifier.padding(16.dp)) {
            // Muestra el username del usuario.
            Text(text = "Username: ${usuario.username}", style = MaterialTheme.typography.titleMedium)
            // Muestra el email del usuario.
            Text(text = "Email: ${usuario.email}", style = MaterialTheme.typography.bodyMedium)
            // Muestra los roles del usuario.
            Text(text = "Roles: ${usuario.roles}", style = MaterialTheme.typography.bodyMedium)
            // Fila que contiene el botón para eliminar el usuario.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { viewModel.deleteUsuario(usuario.username) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar usuario",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}








