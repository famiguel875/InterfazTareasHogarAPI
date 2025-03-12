package com.example.interfazloginregister.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfazloginregister.R
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin

/**
 * MenuScreen
 *
 * Esta pantalla es el menú principal de la aplicación y actúa como centro de navegación.
 * En la parte superior se muestra el logo de la aplicación (recurso .png), seguido de un título
 * y un breve texto explicativo que describe las opciones disponibles. Se incluyen elementos
 * decorativos, como un Divider, para separar visualmente el contenido. Además, se agregaron
 * botones para navegar a las secciones de Tareas, Direcciones, Información y Estadísticas,
 * mientras que el botón de "Usuario Screen" se encuentra deshabilitado.
 *
 * Ahora la pantalla es desplazable (scrollable) para que el contenido se pueda deslizar si es necesario.
 *
 * Requisitos:
 * - Se utiliza el recurso R.drawable.logo para el logo.
 *
 * @param navController Controlador de navegación para transicionar entre pantallas.
 * @param viewModel Instancia del ViewModel de autenticación (AuthViewModelLogin) que provee el token JWT.
 */
@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: AuthViewModelLogin = viewModel() // O se puede utilizar otro ViewModel que maneje la sesión
) {
    // Se recoge el token del ViewModel para fines de depuración.
    val token by viewModel.token.collectAsState()

    // Utilizamos una Column con verticalScroll para permitir el desplazamiento en caso de contenido extenso.
    Column(
        modifier = Modifier
            .fillMaxSize()                      // Ocupa toda la pantalla.
            .padding(16.dp)                     // Espacio interno.
            .verticalScroll(rememberScrollState()), // Habilita el desplazamiento vertical.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Muestra el logo de la aplicación utilizando un recurso .png.
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier
                .size(400.dp)                   // Tamaño grande para resaltar el logo.
                .padding(bottom = 16.dp)
        )

        // Título principal.
        Text("Menú Principal", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Texto explicativo que describe las opciones del menú.
        Text(
            text = "Bienvenido a la aplicación. Utiliza el menú a continuación para navegar a las distintas secciones: " +
                    "Tareas, Direcciones, Información y Estadísticas. Nota: La opción de Usuario está deshabilitada.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Elemento decorativo: Divider.
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón deshabilitado para "Usuario Screen".
        Button(
            onClick = { /* No hace nada ya que está deshabilitado */ },
            enabled = false,  // Deshabilitado.
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Usuario Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de Tareas.
        Button(
            onClick = { navController.navigate("tareaMenu") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tarea Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de Direcciones.
        Button(
            onClick = { navController.navigate("direccionMenu") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dirección Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de Información.
        Button(
            onClick = { navController.navigate("infoScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Info Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de Estadísticas.
        Button(
            onClick = { navController.navigate("estadisticasScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Estadísticas Screen")
        }
    }
}






