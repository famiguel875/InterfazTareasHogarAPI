package com.example.interfazloginregister.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazloginregister.R

/**
 * InfoScreen
 *
 * Esta pantalla muestra información adicional sobre la aplicación, como su misión, características
 * e instrucciones de uso. Se estructura con un Scaffold que contiene una TopAppBar para la navegación,
 * una imagen banner en formato .png y múltiples secciones de texto descriptivo que ofrecen mayor detalle.
 *
 * Recursos utilizados:
 * - Imagen Banner: R.drawable.info (archivo .png para ilustrar la sección).
 *
 * @param navController Controlador de navegación para regresar al menú principal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            // TopAppBar con título e icono de retroceso para volver al menú.
            TopAppBar(
                title = { Text("Información", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Contenedor principal con scroll para permitir visualizar todo el contenido.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen banner representativa de la sección de información.
            Image(
                painter = painterResource(id = R.drawable.info),
                contentDescription = "Banner de Información",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Sección de bienvenida.
            Text(
                text = "Bienvenido a nuestra aplicación. Aquí encontrarás herramientas para gestionar tus tareas, direcciones y usuarios de forma sencilla y eficiente. Nuestra app utiliza autenticación basada en JWT para garantizar la seguridad de tus datos.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Misión de la Aplicación.
            Text(
                text = "Nuestra Misión",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Proveer una plataforma intuitiva y segura que te permita administrar tus actividades diarias de forma organizada y eficiente, facilitando la colaboración y el seguimiento de proyectos.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Características Principales.
            Text(
                text = "Características Principales",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Se muestra una lista de características, cada una en un párrafo.
            Text(
                text = "• Gestión integral de tareas, direcciones y usuarios.\n" +
                        "• Autenticación segura mediante JWT.\n" +
                        "• Interfaz moderna y responsiva con Jetpack Compose.\n" +
                        "• Funcionalidades adicionales para visualizar estadísticas e información de uso.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Instrucciones de Uso.
            Text(
                text = "Instrucciones de Uso",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "1. Inicia sesión o regístrate para comenzar a usar la aplicación.\n" +
                        "2. Accede a las diferentes secciones desde el menú principal.\n" +
                        "3. Utiliza las opciones de agregar, actualizar o eliminar según sea necesario.\n" +
                        "4. Consulta la sección de estadísticas para obtener un resumen del rendimiento.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }
    }
}




