package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin

/**
 * Pantalla principal (MenuScreen) de la aplicación.
 *
 * Esta función Composable muestra el menú principal de la aplicación, que permite la navegación a
 * otras secciones: Usuarios, Tareas y Direcciones. Además, se muestra (de forma opcional) el token
 * JWT almacenado, aunque en producción no se debe exponer.
 *
 * @param navController Controlador de navegación utilizado para transicionar entre pantallas.
 * @param viewModel Instancia del ViewModel que gestiona la sesión (por ejemplo, AuthViewModelLogin).
 *                  Se utiliza para recoger el token JWT y manejar la sesión.
 */
@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: AuthViewModelLogin = viewModel() // O se puede usar otro ViewModel que maneje la sesión
) {
    // Se recoge el token del ViewModel a través de un StateFlow para reflejar los cambios en la UI.
    val token by viewModel.token.collectAsState()

    // Estructura principal de la pantalla utilizando una Column, centrada tanto vertical como horizontalmente.
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla.
            .padding(16.dp), // Espaciado interno.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título del menú principal.
        Text("Menú Principal", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical.

        // Botón para navegar a la pantalla de usuarios.
        Button(onClick = { navController.navigate("usuarioMenu") }) {
            Text("Usuario Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de tareas.
        Button(onClick = { navController.navigate("tareaMenu") }) {
            Text("Tarea Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de direcciones.
        Button(onClick = { navController.navigate("direccionMenu") }) {
            Text("Dirección Screen")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Opcional: Muestra el token almacenado (para fines de depuración; no se recomienda en producción).
        token?.let {
            Text("Token almacenado (no se muestra en producción)")
        }
    }
}

